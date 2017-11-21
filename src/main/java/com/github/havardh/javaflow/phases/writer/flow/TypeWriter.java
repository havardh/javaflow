package com.github.havardh.javaflow.phases.writer.flow;

import java.io.IOException;

import com.github.havardh.javaflow.ast.List;
import com.github.havardh.javaflow.ast.Map;
import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.phases.writer.Writer;
import com.github.havardh.javaflow.phases.writer.flow.converter.Converter;

public class TypeWriter implements Writer<Type> {

  private final Converter converter;

  public TypeWriter(Converter converter) {
    this.converter = converter;
  }

  @Override
  public void write(Type type, java.io.Writer writer) throws IOException {
    if (type instanceof Map) {
      write((Map) type, writer);
    } else if (type instanceof List) {
      write((List) type, writer);
    } else {
      writer.write(converter.convert(type.getCanonicalName()));
    }
  }

  private void write(Map map, java.io.Writer writer) throws IOException {
    writer.write("{[key: ");
    writer.write(converter.convert(map.getKeyType()));
    writer.write("]: ");
    writer.write(converter.convert(map.getValueType()));
    writer.write("}");
  }

  private void write(List list, java.io.Writer writer) throws IOException {
    writer.write(converter.convert(list.getCanonicalName()));
    writer.write("<");
    writer.write(converter.convert(list.getType()));
    writer.write(">");
  }
}

