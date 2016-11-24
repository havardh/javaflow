package no.havard.javaflow;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import no.havard.javaflow.ast.Type;
import no.havard.javaflow.phases.filetransform.FileTransformer;
import no.havard.javaflow.phases.parser.Parser;
import no.havard.javaflow.phases.transform.Transformer;
import no.havard.javaflow.phases.verifier.Verifier;
import no.havard.javaflow.phases.writer.Writer;
import no.havard.javaflow.phases.reader.FileReader;

public class Execution {

  private final FileReader reader;
  private final Parser parser;
  private final List<Transformer> transformers;
  private final List<Verifier> verifiers;
  private final Writer<Type> writer;
  private final List<FileTransformer> fileTransformers;

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

  public String run(String... filenames) {
    List<String> files = read(filenames);
    List<Type> types = parse(files);
    transform(types);
    verify(types);
    String output = write(types);

    return transformFile(output);
  }

  private void verify(List<Type> types) {
    verifiers.forEach(verifier -> verifier.verify(types));
  }

  private List<String> read(String[] filenames) {
    return Stream.of(filenames)
        .map(reader::read)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(toList());
  }

  private List<Type> parse(List<String> files) {
    return files.stream()
        .map(parser::parse)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(toList());
  }

  private void transform(List<Type> types) {
    transformers.forEach(transformer -> transformer.transform(types));
  }

  private String write(List<Type> types) {
    StringWriter stringWriter = new StringWriter();
    types.forEach(type -> {
      try {
        writer.write(type, stringWriter);
      } catch (IOException e) {
        e.printStackTrace();
        System.exit(1);
      }
    });

    return stringWriter.toString();
  }

  private String transformFile(String inputFile) {
    String file = inputFile;

    for (FileTransformer transformer : fileTransformers) {
      file = transformer.transform(file);
    }

    return file;
  }
}

