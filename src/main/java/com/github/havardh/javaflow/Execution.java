package com.github.havardh.javaflow;

import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.phases.filetransform.FileTransformer;
import com.github.havardh.javaflow.phases.parser.Parser;
import com.github.havardh.javaflow.phases.reader.FileReader;
import com.github.havardh.javaflow.phases.transform.Transformer;
import com.github.havardh.javaflow.phases.verifier.Verifier;
import com.github.havardh.javaflow.phases.writer.Writer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
   * @param filenames list of files to create flow types for.
   * @return the flow type as a {@code String}
   */
  public String run(String... filenames) {
    List<String> files = read(filenames);
    List<Type> types = parse(files);
    transform(types);
    verify(types);
    String output = write(types);

    return transformFile(output);
  }

  /**
   * Runs all {@code verifiers} on the list of {$code types}
   *
   * @param types list of JavaFlow internal type
   * @throws com.github.havardh.javaflow.exceptions.MissingTypeException when
   * a type is references which is not in the given set of types or overrides
   */
  private void verify(List<Type> types) {
    verifiers.forEach(verifier -> verifier.verify(types));
  }

  /**
   * Read each {@code file} into a list of {@code String} with the {@code reader}.
   *
   * @param filenames list of files to read
   * @return list of file content
   */
  private List<String> read(String[] filenames) {
    return Stream.of(filenames)
        .map(reader::read)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(toList());
  }

  /**
   * Parse each {@code file} to a list of {@code Type}. The {@code Type} is the
   * internal representation of a model.
   *
   * @param files list of file contents
   * @return list of internal types
   */
  private List<Type> parse(List<String> files) {
    return files.stream()
        .map(parser::parse)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(toList());
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

    for (int i=0; i<types.size(); i++) {
      if (i != 0) {
        stringWriter.write("\n");
      }
      try {
        writer.write(types.get(i), stringWriter);
      } catch (IOException e) {
        e.printStackTrace();
        System.exit(1);
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

