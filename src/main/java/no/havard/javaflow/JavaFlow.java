package no.havard.javaflow;

import static java.util.Arrays.asList;

import no.havard.javaflow.phases.reader.java.JavaReader;
import no.havard.javaflow.phases.transform.InheritanceTransformer;
import no.havard.javaflow.phases.transform.SortedTypeTransformer;
import no.havard.javaflow.phases.writer.flow.converter.Converter;
import no.havard.javaflow.phases.writer.flow.FlowWriter;
import no.havard.javaflow.phases.writer.flow.converter.JavaFlowConverter;

public class JavaFlow {

  public static void main(String args[]) {
    Converter converter = new JavaFlowConverter("types.yml");

    Execution execution = new Execution(
        new JavaReader(),
        asList(
            new InheritanceTransformer(),
            new SortedTypeTransformer()
        ),
        new FlowWriter(converter)
    );

    System.out.println(execution.run(args));
  }

}
