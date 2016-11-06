package no.havard.javaflow.phases.writer.flow;

import java.io.IOException;

import no.havard.javaflow.ast.Class;
import no.havard.javaflow.ast.Enum;
import no.havard.javaflow.ast.Type;
import no.havard.javaflow.phases.writer.Writer;

public class FlowWriter implements Writer<Type> {

  private final ClassWriter classWriter = new ClassWriter();
  private final EnumWriter enumWriter = new EnumWriter();

  @Override
  public void write(Type type, java.io.Writer writer) throws IOException {
    if (type instanceof Class) {
      classWriter.write((Class) type, writer);
    } else if (type instanceof Enum) {
      enumWriter.write((Enum) type, writer);
    }
    writer.write("\n\n");
  }

}
