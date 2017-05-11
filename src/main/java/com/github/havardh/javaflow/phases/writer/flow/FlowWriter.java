package com.github.havardh.javaflow.phases.writer.flow;

import java.io.IOException;

import com.github.havardh.javaflow.ast.Class;
import com.github.havardh.javaflow.ast.Enum;
import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.phases.writer.Writer;
import com.github.havardh.javaflow.phases.writer.flow.converter.Converter;

/**
 * {@code Writer} for writing flow types for {@code Type}.
 */
public class FlowWriter implements Writer<Type> {

  private final ClassWriter classWriter;
  private final EnumWriter enumWriter;

  /**
   * Create a flow type {@code Writer}
   *
   * @param converter type converter to convert types from source language
   *                  flow types.
   */
  public FlowWriter(Converter converter) {
     classWriter = new ClassWriter(converter);
     enumWriter = new EnumWriter();
  }

  /** {@inheritDoc} */
  @Override
  public void write(Type type, java.io.Writer writer) throws IOException {
    if (type instanceof Class) {
      classWriter.write((Class) type, writer);
    } else if (type instanceof Enum) {
      enumWriter.write((Enum) type, writer);
    }
  }

}
