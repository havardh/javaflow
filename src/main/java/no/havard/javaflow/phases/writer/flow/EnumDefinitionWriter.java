package no.havard.javaflow.phases.writer.flow;

import java.io.IOException;

import no.havard.javaflow.model.EnumDefinition;
import no.havard.javaflow.phases.writer.Writer;

class EnumDefinitionWriter implements Writer<EnumDefinition> {
  @Override
  public void write(EnumDefinition enumDefinition, java.io.Writer writer) throws IOException {
    writer.write("export type ");
    writer.write(enumDefinition.getName());
    writer.write(" = ");
    writeValues(enumDefinition, writer);
    writer.write(";\n");
  }

  private void writeValues(EnumDefinition enumDefinition, java.io.Writer writer) throws IOException {
    for (String value : enumDefinition.getValues()) {
      writer.write("\n  | \"");
      writer.write(value);
      writer.write("\"");
    }
  }
}

