package no.havard.javaflow.phases.writer.flow;

import java.io.IOException;

import no.havard.javaflow.model.Enum;
import no.havard.javaflow.phases.writer.Writer;

class EnumWriter implements Writer<Enum> {
  @Override
  public void write(Enum anEnum, java.io.Writer writer) throws IOException {
    writer.write("export type ");
    writer.write(anEnum.getName());
    writer.write(" = ");
    writeValues(anEnum, writer);
    writer.write(";\n");
  }

  private void writeValues(Enum anEnum, java.io.Writer writer) throws IOException {
    for (String value : anEnum.getValues()) {
      writer.write("\n  | \"");
      writer.write(value);
      writer.write("\"");
    }
  }
}

