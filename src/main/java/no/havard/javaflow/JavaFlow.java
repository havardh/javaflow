package no.havard.javaflow;

import static java.util.Arrays.asList;

import no.havard.javaflow.phases.reader.java.JavaReader;
import no.havard.javaflow.phases.transform.InheritanceTransformer;
import no.havard.javaflow.phases.transform.SortedTypeTransformer;
import no.havard.javaflow.phases.writer.flow.FlowWriter;
import no.havard.javaflow.phases.writer.flow.JavaFlowTypeConversion;

public class JavaFlow {

  public static void main(String args[]) {
    JavaFlowTypeConversion.init();

    Execution execution = new Execution(
        new JavaReader(),
        asList(
            new InheritanceTransformer(),
            new SortedTypeTransformer()
        ),
        new FlowWriter()
    );

    System.out.println(execution.run(args));
  }

}
