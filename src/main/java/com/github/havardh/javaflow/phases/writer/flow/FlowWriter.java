package com.github.havardh.javaflow.phases.writer.flow;

import java.io.IOException;

import com.github.havardh.javaflow.ast.Class;
import com.github.havardh.javaflow.ast.Enum;
import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.phases.writer.Writer;
import com.github.havardh.javaflow.phases.writer.flow.converter.Converter;

public class FlowWriter implements Writer<Type> {

  private final ClassWriter classWriter;
  private final EnumWriter enumWriter;

  public FlowWriter(Converter converter) {
     classWriter = new ClassWriter(converter);
     enumWriter = new EnumWriter();
  }

  @Override
  public void write(Type type, java.io.Writer writer) throws IOException {
    if (type instanceof Class) {
      classWriter.write((Class) type, writer);
    } else if (type instanceof Enum) {
      enumWriter.write((Enum) type, writer);
    }
  }

}
