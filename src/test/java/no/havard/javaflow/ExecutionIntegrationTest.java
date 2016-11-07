package no.havard.javaflow;

import static java.util.Arrays.asList;

import static no.havard.javaflow.testutil.Assertions.assertStringEqual;

import no.havard.javaflow.phases.parser.java.JavaParser;
import no.havard.javaflow.phases.reader.FileReader;
import no.havard.javaflow.phases.transform.InheritanceTransformer;
import no.havard.javaflow.phases.transform.SortedTypeTransformer;
import no.havard.javaflow.phases.writer.flow.FlowWriter;
import no.havard.javaflow.phases.writer.flow.converter.JavaFlowConverter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExecutionIntegrationTest {

  private static final String BASE_PATH = "src/test/java/no/havard/javaflow/model/";

  private Execution execution;

  @BeforeEach
  public void setup() {
    this.execution = new Execution(
        new FileReader(),
        new JavaParser(),
        asList(
            new InheritanceTransformer(),
            new SortedTypeTransformer()
        ),
        new FlowWriter(new JavaFlowConverter())
    );
  }

  @Test
  public void shouldParseModel() {
    String flowCode = execution.run(BASE_PATH + "Model.java");

    assertStringEqual(flowCode,
        "/* @flow */",
        "export type Model = {",
        "  yolo: string,",
        "};"
    );
  }

  @Test
  public void shouldParseEnum() {
    String flowCode = execution.run(BASE_PATH + "Enumeration.java");

    assertStringEqual(flowCode,
        "/* @flow */",
        "export type Enumeration = ",
        "  | \"ONE\"",
        "  | \"TWO\";"
    );
  }

}

