package com.github.havardh.javaflow.phases.writer.flow;

import java.io.IOException;

import com.github.havardh.javaflow.ast.Field;
import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.phases.writer.Writer;
import com.github.havardh.javaflow.phases.writer.flow.converter.Converter;

/**
 * {@code Writer} for writing {@code Field}
 */
public class FieldDefinitionWriter implements Writer<Field> {

  private Writer<Type> typeWriter;

  /**
   * Create a {@code Writer} for {@code Field}
   *
   * @param converter type converter to convert types from source language
   *                  flow types.
   */
  public FieldDefinitionWriter(Converter converter) {
    this.typeWriter = new TypeWriter(converter);
  }

  /** {@inheritDoc} */
  @Override
  public void write(Field field, java.io.Writer writer) throws IOException {
    if (field.isIgnored()) {
      return;
    }
    writer.write(field.getName());
    writer.write(": ");
    if (field.isNullable()) {
      writer.write("?");
    }
    typeWriter.write(field.getType(), writer);
  }
}

