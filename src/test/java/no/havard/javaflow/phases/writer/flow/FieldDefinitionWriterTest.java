package no.havard.javaflow.phases.writer.flow;

import static no.havard.javaflow.model.builders.FieldDefinitionBuilder.fieldDefinition;
import static no.havard.javaflow.model.fixtures.TypeFixtures.stringType;

import java.io.IOException;

import no.havard.javaflow.model.FieldDefinition;
import no.havard.javaflow.phases.writer.WriterTest;

import org.junit.jupiter.api.Test;

public class FieldDefinitionWriterTest extends WriterTest<FieldDefinition> {

  public FieldDefinitionWriterTest() {
    super(new FieldDefinitionWriter());
  }

  @Test
  public void shouldSerializeFieldDefinition() throws IOException {
    String flow = toFlow(fieldDefinition()
        .withName("field")
        .withType(stringType().build())
        .build());

    assertStringEqual(flow,
        "field: string"
    );
  }

  @Test
  public void shouldPrependTypeOfNullableWithQuestionMark() throws IOException {
    String flow = toFlow(fieldDefinition()
        .withName("field")
        .withIsNullable(true)
        .withType(stringType().build())
        .build());

    assertStringEqual(flow,
        "field: ?string"
    );
  }

}

