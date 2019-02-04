package com.github.havardh.javaflow.phases.writer.flow;

import static com.github.havardh.javaflow.model.fixtures.FieldDefinitionFixtures.stringFieldDefinition;
import static com.github.havardh.javaflow.testutil.Assertions.assertStringEqual;
import static com.github.havardh.javaflow.testutil.MapConverterFixture.stringMap;

import java.io.IOException;

import com.github.havardh.javaflow.ast.Class;
import com.github.havardh.javaflow.phases.writer.WriterTest;

import org.junit.jupiter.api.Test;

import com.github.havardh.javaflow.ast.builders.ClassBuilder;

public class ClassWriterTest extends WriterTest<Class> {

  public ClassWriterTest() {
    super(new ClassWriter(stringMap().build()));
  }

  @Test
  public void shouldWriteClassDefinitionForEmptyModel() throws IOException {
    String flow = toFlow(ClassBuilder.classBuilder().withName("Model").build());

    assertStringEqual(flow, "export type Model = {};");
  }

  @Test
  public void shouldFieldDefinitions() throws IOException {
    String flow = toFlow(ClassBuilder.classBuilder()
      .withName("Model")
      .withField(stringFieldDefinition().build())
      .build());

    assertStringEqual(flow,
        "export type Model = {",
        "  field: string,",
        "};"
    );
  }

  @Test
  public void shouldSkipIgnoredFieldDefinitions() throws IOException {
    String flow = toFlow(ClassBuilder.classBuilder()
        .withName("Model")
        .withField(stringFieldDefinition().withIsIgnored(true).build())
        .build());

    assertStringEqual(flow,
        "export type Model = {",
        "};"
    );
  }

}

