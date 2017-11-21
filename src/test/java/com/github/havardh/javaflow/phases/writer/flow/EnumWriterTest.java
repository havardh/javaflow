package com.github.havardh.javaflow.phases.writer.flow;

import static com.github.havardh.javaflow.testutil.Assertions.assertStringEqual;

import java.io.IOException;

import com.github.havardh.javaflow.ast.Enum;
import com.github.havardh.javaflow.ast.builders.EnumBuilder;
import com.github.havardh.javaflow.phases.writer.WriterTest;

import org.junit.jupiter.api.Test;

public class EnumWriterTest extends WriterTest<Enum> {

  public EnumWriterTest() {
    super(new EnumWriter());
  }

  @Test
  public void shouldSerializeEnum() throws IOException {
    String flow = toFlow(EnumBuilder.enumBuilder()
        .withName("Enum")
        .withEnumValue("Value1")
        .withEnumValue("Value2")
        .build());

    assertStringEqual(flow,
        "export type Enum =",
        "  | \"Value1\"",
        "  | \"Value2\";"
    );
  }
}

