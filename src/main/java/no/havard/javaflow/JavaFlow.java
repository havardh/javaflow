package no.havard.javaflow;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import no.havard.javaflow.ast.Type;
import no.havard.javaflow.phases.reader.Reader;
import no.havard.javaflow.phases.reader.java.JavaReader;
import no.havard.javaflow.phases.transform.InheritanceTransformer;
import no.havard.javaflow.phases.transform.Transformer;
import no.havard.javaflow.phases.writer.Writer;
import no.havard.javaflow.phases.writer.flow.FlowWriter;
import no.havard.javaflow.phases.writer.flow.JavaFlowTypeConversion;

public class JavaFlow {

  private static Reader reader = new JavaReader();
  private static Transformer transformer = new InheritanceTransformer();
  private static Writer<Type> writer = new FlowWriter();

  public static void main(String args[]) {
    JavaFlowTypeConversion.init();
    System.out.print(convert(args));
  }

  private static String convert(String[] filenames) {
    StringWriter stringWriter = new StringWriter();

    stringWriter.write("/* @flow */\n");
    List<Type> types = parseAll(filenames);
    transformer.transform(types);
    types.forEach(t -> {
      try {
        writer.write(t, stringWriter);
      } catch (IOException e) {
        e.printStackTrace();
        System.exit(1);
      }
    });

    return stringWriter.toString();
  }

  static List<Type> parseAll(String[] filenames) {
    return Stream.of(filenames)
        .map(reader::read)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(toList());
  }

}
