package no.havard.javaflow.phases.writer.flow;

import static no.havard.javaflow.ast.builders.ClassBuilder.classBuilder;
import static no.havard.javaflow.model.fixtures.FieldDefinitionFixtures.stringFieldDefinition;

import java.io.IOException;

import no.havard.javaflow.ast.Class;
import no.havard.javaflow.phases.writer.WriterTest;

import org.junit.jupiter.api.Test;

public class ClassWriterTest extends WriterTest<Class> {

  public ClassWriterTest() {
    super(new ClassWriter());
  }

  @Test
  public void shouldWriteClassDefinitionForEmptyModel() throws IOException {
    String flow = toFlow(classBuilder().withName("Model").build());

    assertStringEqual(flow, "export type Model = {};");
  }

  @Test
  public void shouldFieldDefinitions() throws IOException {
    String flow = toFlow(classBuilder()
      .withName("Model")
      .withField(stringFieldDefinition().build())
      .build());

    assertStringEqual(flow,
        "export type Model = {",
        "  field: string,",
        "};"
    );
  }

}

