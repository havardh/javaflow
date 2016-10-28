package no.havard.javaflow;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import no.havard.javaflow.model.Definition;
import no.havard.javaflow.phases.reader.Reader;
import no.havard.javaflow.phases.reader.java.JavaReader;
import no.havard.javaflow.phases.transform.InheritanceTransformer;
import no.havard.javaflow.phases.transform.Transformer;
import no.havard.javaflow.phases.writer.Writer;
import no.havard.javaflow.phases.writer.flow.FlowWriter;

public class JavaFlow {

  private static Reader reader = new JavaReader();
  private static Transformer transformer = new InheritanceTransformer();
  private static Writer<Definition> writer = new FlowWriter();

  public static void main(String args[]) {
    JavaFlowTypeConversion.init();

    System.out.println("/* @flow */");
    List<Definition> definitions = parseAll(args);
    transformer.transform(definitions);
    definitions.stream().forEach(writer::write);
  }

  public static List<Definition> parseAll(String[] filenames) {
    return Stream.of(filenames)
        .map(reader::read)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(toList());
  }

}
