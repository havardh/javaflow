package com.github.havardh.javaflow.phases.writer.flow;

import java.io.IOException;

import com.github.havardh.javaflow.ast.Enum;
import com.github.havardh.javaflow.phases.writer.Writer;

/**
 * {@code Writer} for writing {@code Enum}
 */
class EnumWriter implements Writer<Enum> {

  /** {@inheritDoc} */
  @Override
  public void write(Enum anEnum, java.io.Writer writer) throws IOException {
    writer.write("export type ");
    writer.write(anEnum.getCanonicalName().getName());
    writer.write(" =");
    writeValues(anEnum, writer);
    writer.write(";");
  }

  private void writeValues(Enum anEnum, java.io.Writer writer) throws IOException {
    for (String value : anEnum.getValues()) {
      writer.write("\n  | \"");
      writer.write(value);
      writer.write("\"");
    }
  }
}

