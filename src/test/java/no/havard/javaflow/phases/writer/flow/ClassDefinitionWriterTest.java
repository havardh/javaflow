package no.havard.javaflow.phases.writer.flow;

import static no.havard.javaflow.model.builders.ClassDefinitionBuilder.classDefinitionBuilder;
import static no.havard.javaflow.model.fixtures.FieldDefinitionFixtures.stringFieldDefinition;

import java.io.IOException;

import no.havard.javaflow.model.ClassDefinition;
import no.havard.javaflow.phases.writer.WriterTest;

import org.junit.jupiter.api.Test;

public class ClassDefinitionWriterTest extends WriterTest<ClassDefinition> {

  public ClassDefinitionWriterTest() {
    super(new ClassDefinitionWriter());
  }

  @Test
  public void shouldWriteClassDefinitionForEmptyModel() throws IOException {
    String flow = toFlow(classDefinitionBuilder()
        .withName("Model")
        .build());

    assertStringEqual(flow, "export type Model = {};");
  }

  @Test
  public void shouldFieldDefinitions() throws IOException {
    String flow = toFlow(classDefinitionBuilder()
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

