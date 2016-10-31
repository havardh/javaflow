package no.havard.javaflow.phases.writer.flow;

import java.io.IOException;

import no.havard.javaflow.ast.Class;
import no.havard.javaflow.ast.Definition;
import no.havard.javaflow.ast.Enum;
import no.havard.javaflow.phases.writer.Writer;

public class FlowWriter implements Writer<Definition> {

  private final ClassWriter classWriter = new ClassWriter();
  private final EnumWriter enumWriter = new EnumWriter();

  @Override
  public void write(Definition definition, java.io.Writer writer) throws IOException {
    if (definition instanceof Class) {
      classWriter.write((Class) definition, writer);
    } else if (definition instanceof Enum) {
      enumWriter.write((Enum) definition, writer);
    }
    writer.write("\n\n");
  }

}
