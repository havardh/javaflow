package no.havard.javaflow.phases.writer.flow;

import static no.havard.javaflow.model.builders.FieldBuilder.fieldDefinition;
import static no.havard.javaflow.model.fixtures.TypeFixtures.stringType;

import java.io.IOException;

import no.havard.javaflow.model.Field;
import no.havard.javaflow.phases.writer.WriterTest;

import org.junit.jupiter.api.Test;

public class FieldWriterTest extends WriterTest<Field> {

  public FieldWriterTest() {
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

