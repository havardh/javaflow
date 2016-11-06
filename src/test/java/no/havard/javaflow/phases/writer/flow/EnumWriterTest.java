package no.havard.javaflow.phases.writer.flow;

import java.io.IOException;

import no.havard.javaflow.ast.Enum;
import no.havard.javaflow.ast.builders.EnumBuilder;
import no.havard.javaflow.phases.writer.WriterTest;

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
        "export type Enum = ",
        "  | \"Value1\"",
        "  | \"Value2\";"
    );
  }
}

