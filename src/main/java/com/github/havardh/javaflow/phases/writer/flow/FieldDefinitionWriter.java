package com.github.havardh.javaflow.phases.writer.flow;

import java.io.IOException;

import com.github.havardh.javaflow.ast.Field;
import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.phases.writer.Writer;
import com.github.havardh.javaflow.phases.writer.flow.converter.Converter;

public class FieldDefinitionWriter implements Writer<Field> {

  private Writer<Type> typeWriter;

  public FieldDefinitionWriter(Converter converter) {
    this.typeWriter = new TypeWriter(converter);
  }

  @Override
  public void write(Field field, java.io.Writer writer) throws IOException {
    writer.write(field.getName());
    writer.write(": ");
    if (field.isNullable()) {
      writer.write("?");
    }
    typeWriter.write(field.getType(), writer);
  }
}

