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

  private String transformFile(String inputFile) {
    String file = inputFile;

    for (FileTransformer transformer : fileTransformers) {
      file = transformer.transform(file);
    }

    return file;
  }
}

