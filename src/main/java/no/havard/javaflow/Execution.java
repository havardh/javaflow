package no.havard.javaflow;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import no.havard.javaflow.ast.Type;
import no.havard.javaflow.phases.parser.Parser;
import no.havard.javaflow.phases.transform.Transformer;
import no.havard.javaflow.phases.writer.Writer;
import no.havard.javaflow.phases.reader.FileReader;

public class Execution {

  private final FileReader reader;
  private final Parser parser;
  private final List<Transformer> transformers;
  private final Writer<Type> writer;

  public Execution(
      FileReader reader,
      Parser parser,
      List<Transformer> transformers,
      Writer<Type> writer
  ) {
    this.reader = reader;
    this.parser = parser;
    this.transformers = transformers;
    this.writer = writer;
  }

  public String run(String filenames[]) {
    List<String> files = read(filenames);
    List<Type> types = parse(files);
    transform(types);
    return write(types);
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
    stringWriter.write("/* @flow */\n");
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
}

