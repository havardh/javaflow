package com.github.havardh.javaflow.phases.writer.flow;

import static com.github.havardh.javaflow.ast.builders.FieldBuilder.fieldBuilder;
import static com.github.havardh.javaflow.testutil.Assertions.assertStringEqual;
import static com.github.havardh.javaflow.testutil.MapConverterFixture.stringMap;

import java.io.IOException;

import com.github.havardh.javaflow.ast.Field;
import com.github.havardh.javaflow.phases.writer.WriterTest;

import org.junit.jupiter.api.Test;

import com.github.havardh.javaflow.model.fixtures.TypeFixtures;

public class FieldWriterTest extends WriterTest<Field> {

  public FieldWriterTest() {
    super(new FieldDefinitionWriter(stringMap().build()));
  }

  @Test
  public void shouldSerializeFieldDefinition() throws IOException {
    String flow = toFlow(fieldBuilder()
        .withName("field")
        .withType(TypeFixtures.stringType().build())
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
        .withType(TypeFixtures.stringType().build())
        .build());

    assertStringEqual(flow,
        "field: ?string"
    );
  }

}

