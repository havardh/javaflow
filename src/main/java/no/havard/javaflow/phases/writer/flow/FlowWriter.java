package no.havard.javaflow.phases.writer.flow;

import java.io.IOException;

import no.havard.javaflow.ast.Class;
import no.havard.javaflow.ast.Enum;
import no.havard.javaflow.ast.Type;
import no.havard.javaflow.phases.writer.Writer;
import no.havard.javaflow.phases.writer.flow.converter.Converter;

public class FlowWriter implements Writer<Type> {

  private final ClassWriter classWriter;
  private final EnumWriter enumWriter;

  public FlowWriter(Converter converter) {
     classWriter = new ClassWriter(converter);
     enumWriter = new EnumWriter();
  }

  @Override
  public void write(Type type, java.io.Writer writer) throws IOException {
    writer.write("\n");
    if (type instanceof Class) {
      classWriter.write((Class) type, writer);
    } else if (type instanceof Enum) {
      enumWriter.write((Enum) type, writer);
    }
    writer.write("\n");
  }

}
