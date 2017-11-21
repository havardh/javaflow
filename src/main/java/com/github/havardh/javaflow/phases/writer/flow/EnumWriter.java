package com.github.havardh.javaflow.phases.writer.flow;

import java.io.IOException;

import com.github.havardh.javaflow.ast.Enum;
import com.github.havardh.javaflow.phases.writer.Writer;

class EnumWriter implements Writer<Enum> {
  @Override
  public void write(Enum anEnum, java.io.Writer writer) throws IOException {
    writer.write("export type ");
    writer.write(anEnum.getName());
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

