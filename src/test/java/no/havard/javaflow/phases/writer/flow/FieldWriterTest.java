package no.havard.javaflow.phases.writer.flow;

import static no.havard.javaflow.ast.builders.FieldBuilder.fieldBuilder;
import static no.havard.javaflow.model.fixtures.TypeFixtures.stringType;
import static no.havard.javaflow.testutil.Assertions.assertStringEqual;
import static no.havard.javaflow.testutil.MapConverterFixture.stringMap;

import java.io.IOException;

import no.havard.javaflow.ast.Field;
import no.havard.javaflow.phases.writer.WriterTest;

import org.junit.jupiter.api.Test;

public class FieldWriterTest extends WriterTest<Field> {

  public FieldWriterTest() {
    super(new FieldDefinitionWriter(stringMap().build()));
  }

  @Test
  public void shouldSerializeFieldDefinition() throws IOException {
    String flow = toFlow(fieldBuilder()
        .withName("field")
        .withType(stringType().build())
        .build());

    assertStringEqual(flow,
        "field: string"
    );
  }

  @Test
  public void shouldPrependTypeOfNullableWithQuestionMark() throws IOException {
    String flow = toFlow(fieldBuilder()
        .withName("field")
        .withIsNullable(true)
        .withType(stringType().build())
        .build());

    assertStringEqual(flow,
        "field: ?string"
    );
  }

}

