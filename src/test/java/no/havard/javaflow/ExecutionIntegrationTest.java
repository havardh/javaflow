package no.havard.javaflow;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import static no.havard.javaflow.testutil.Assertions.assertStringEqual;
import static no.havard.javaflow.util.TypeMap.emptyTypeMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.fail;

import no.havard.javaflow.exceptions.MissingTypeException;
import no.havard.javaflow.phases.parser.java.JavaParser;
import no.havard.javaflow.phases.reader.FileReader;
import no.havard.javaflow.phases.transform.InheritanceTransformer;
import no.havard.javaflow.phases.transform.SortedTypeTransformer;
import no.havard.javaflow.phases.verifier.MemberFieldsPresentVerifier;
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
        singletonList(new MemberFieldsPresentVerifier(emptyTypeMap())),
        new FlowWriter(new JavaFlowConverter()),
        emptyList()
    );
  }

  @Test
  public void shouldParseModel() {
    String flowCode = execution.run(BASE_PATH + "Model.java");

    assertStringEqual(flowCode,
        "export type Model = {",
        "  yolo: string,",
        "};"
    );
  }

  @Test
  public void shouldParseEnum() {
    String flowCode = execution.run(BASE_PATH + "Enumeration.java");

    assertStringEqual(flowCode,
        "export type Enumeration =",
        "  | \"ONE\"",
        "  | \"TWO\";"
    );
  }

  @Test
  public void shouldParseModelWithList() {
    String flowCode = execution.run(BASE_PATH + "ModelWithList.java");

    assertStringEqual(flowCode,
        "export type ModelWithList = {",
        "  words: Array<string>,",
        "};");
  }

  @Test
  public void shouldParseModelWithMap() {
    String flowCode = execution.run(BASE_PATH + "ModelWithMap.java");

    assertStringEqual(flowCode,
        "export type ModelWithMap = {",
        "  field: {[key: string]: number},",
        "};");
  }

  @Test
  public void shouldParseModelWithCollection() {
    String flowCode = execution.run(BASE_PATH + "ModelWithCollection.java");

    assertStringEqual(flowCode,
        "export type ModelWithCollection = {",
        "  strings: Array<string>,",
        "};");
  }

  @Test
  public void shouldParseModelWithMember() {
    String flowCode = execution.run(
        BASE_PATH + "Wrapper.java",
        BASE_PATH + "Member.java",
        BASE_PATH + "packaged/PackagedMember.java"
    ) + "\n";

    System.out.print(flowCode);

    assertStringEqual(flowCode,
        "export type Member = {};",
        "",
        "export type PackagedMember = {};",
        "",
        "export type Wrapper = {",
        "  member: Member,",
        "  packagedMember: PackagedMember,",
        "};"
    );
  }

  @Test
  public void shouldThrowExceptionWhenFieldIsNotFound() {
    try {
      execution.run(BASE_PATH + "Wrapper.java");
      fail("Should fail when member types are not found");
    } catch (MissingTypeException e) {
      assertThat(e.getTypes().entrySet(), hasSize(1));
    }
  }
}

