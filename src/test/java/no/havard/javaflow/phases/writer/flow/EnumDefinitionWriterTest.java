package no.havard.javaflow.phases.writer.flow;

import java.io.IOException;

import no.havard.javaflow.model.EnumDefinition;
import no.havard.javaflow.model.builders.EnumDefinitionBuilder;
import no.havard.javaflow.phases.writer.WriterTest;

import org.junit.jupiter.api.Test;

public class EnumDefinitionWriterTest extends WriterTest<EnumDefinition> {

  public EnumDefinitionWriterTest() {
    super(new EnumDefinitionWriter());
  }

  @Test
  public void shouldSerializeEnum() throws IOException {
    String flow = toFlow(EnumDefinitionBuilder.enumDefinitionBuilder()
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

