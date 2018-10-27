package com.github.havardh.javaflow;

import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.exceptions.AggregatedException;
import com.github.havardh.javaflow.exceptions.ExitException;
import com.github.havardh.javaflow.exceptions.ExitException.ErrorCode;
import com.github.havardh.javaflow.phases.filetransform.FileTransformer;
import com.github.havardh.javaflow.phases.parser.Parser;
import com.github.havardh.javaflow.phases.reader.FileReader;
import com.github.havardh.javaflow.phases.transform.Transformer;
import com.github.havardh.javaflow.phases.verifier.Verifier;
import com.github.havardh.javaflow.phases.writer.Writer;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * JavaFlow Execution runner
 *
 * The Execution object is set up with the readers, parsers,
 * transformers, verifiers, writers and file transformers.
 * Then the {@code run} method will run the execution for
 * the given setup.
 */
public class Execution {

  private final FileReader reader;
  private final Parser parser;
  private final List<Transformer> transformers;
  private final List<Verifier> verifiers;
  private final Writer<Type> writer;
  private final List<FileTransformer> fileTransformers;

  /**
   * Sets up an Execution with the given set of configurations.
   *
   * @param reader           - file reader
   * @param parser           - code parser
   * @param transformers     - JavaFlow transforms
   * @param verifiers        - JavaFlow verifiers
   * @param writer           - string writer
   * @param fileTransformers - output file transforms
   */
  public Execution(
      FileReader reader,
      Parser parser,
      List<Transformer> transformers,
      List<Verifier> verifiers,
      Writer<Type> writer,
      List<FileTransformer> fileTransformers
  ) {
    this.reader = reader;
    this.parser = parser;
    this.transformers = transformers;
    this.verifiers = verifiers;
    this.writer = writer;
    this.fileTransformers = fileTransformers;
  }

  /**
   * Run the execution on the given set of files.
   *
   * This is the heart of the JavaFlow program. This method will read
   * each file with the file {@code reader}, parse the source code with
   * the {@code parser}. Next it will run all the {@code transforms} and
   * {@code verifiers}, before it writes the results to a {@code String}
   * with the {@code writer}. This string is transformed by the
   * {@code fileTransformers} before the result is returned to the caller.
   *
   * @param patterns list of file patterns to create flow types for.
   * @return the flow type as a {@code String}
   */
  public String run(String... patterns) {
    return Stream.of(resolve(patterns))
        .map(this::parse)
        .peek(this::transform)
        .peek(this::verify)
        .map(this::write)
        .map(this::transformFile)
        .collect(Collectors.joining());
  }

  /**
   * Runs all {@code verifiers} on the list of {$code types}
   *
   * @param types list of JavaFlow internal type
   * @throws com.github.havardh.javaflow.exceptions.MissingTypeException when
   * a type is references which is not in the given set of types or overrides
   */
  private void verify(List<Type> types) {
    List<Exception> exceptions = new ArrayList<>();
    for (Verifier verifier : verifiers) {
      try {
        verifier.verify(types);
      } catch (Exception e) {
        exceptions.add(e);
      }
    }

    if (!exceptions.isEmpty()) {
      throw new AggregatedException("Verification failed:\n", exceptions, false);
    }
  }

  private List<Path> resolve(String... patterns) {
    List<Path> paths = new ArrayList<>();
    Path workdir = Paths.get(".");
    for (String pattern : patterns) {
      Path path = workdir.resolve(pattern);
      if (Files.isRegularFile(path)) {
        paths.add(path);
      } else {
        Path start;
        PathMatcher pathMatcher;
        if (Files.isDirectory(path)) {
          start = path;
          pathMatcher = (p) -> true;
        } else {
          start = workdir;
          pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        }
        try {
          Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
              if (attrs.isRegularFile() && pathMatcher.matches(path)) {
                paths.add(path);
              }
              return FileVisitResult.CONTINUE;
            }
          });
        } catch (IOException e) {
          throw new ExitException(ErrorCode.COULD_NOT_READ_FILE, e);
        }
      }
    }
    return paths;
  }

  /**
   * Read each {@code file} into a list of {@code String} with the {@code reader}.
   *
   * @param filenames list of files to read
   * @return list of file content
   */
  private List<File> list(String[] filenames) {
    return Stream.of(filenames)
        .map(File::new)
        .flatMap(this::listDirectory)
        .collect(toList());
  }

  private Stream<File> listDirectory(File file) {
    File[] files = file.listFiles();
    if (files == null) {
      // not a directory!
      return file.getName().endsWith(".java") ? Stream.of(file) : Stream.empty();
    } else {
      return Arrays.stream(files).flatMap(this::listDirectory);
    }
  }

  /**
   * Parse each {@code path} to a list of {@code Type}. The {@code Type} is the
   * internal representation of a model.
   *
   * @param paths list of file paths
   * @return list of internal types
   */
  private List<Type> parse(List<Path> paths) {
    return paths.stream()
        .flatMap(path -> parse(path).stream())
        .collect(toList());
  }

  private List<Type> parse(Path path) {
    Optional<String> source = reader.read(path.toFile().getPath());
    if (source.isPresent()) {
      try {
        return parser.parse(source.get());
      } catch (RuntimeException e) {
        throw new IllegalStateException("Failed to parse file: " + path, e);
      }
    }
    return emptyList();
  }

  /**
   * Transforms each {@code type} with the set of {@code transformers}
   *
   * @param types list of types
   */
  private void transform(List<Type> types) {
    transformers.forEach(transformer -> transformer.transform(types));
  }

  /**
   * Writes the list of {@code Type} into a {@code String}
   *
   * @param types list of types
   * @return the flow types as a {@code String}
   */
  private String write(List<Type> types) {
    StringWriter stringWriter = new StringWriter();

    for (int i = 0; i < types.size(); i++) {
      if (i != 0) {
        stringWriter.write("\n");
      }
      try {
        writer.write(types.get(i), stringWriter);
      } catch (IOException e) {
        throw new ExitException(ErrorCode.COULD_NOT_WRITE_OUTPUT, e);
      }
      if (i != types.size() - 1) {
        stringWriter.write("\n");
      }
    }

    return stringWriter.toString();
  }

  /**
   * Transforms a output file with the set of {@code fileTransformers}
   *
   * @param inputFile the output file
   * @return transformed output file
   */
  private String transformFile(String inputFile) {
    String file = inputFile;

    for (FileTransformer transformer : fileTransformers) {
      file = transformer.transform(file);
    }

    return file;
  }
}

