package no.havard.javaflow.phases.writer.flow;

import java.io.IOException;

import no.havard.javaflow.ast.Field;
import no.havard.javaflow.ast.Type;
import no.havard.javaflow.phases.writer.Writer;

public class FieldDefinitionWriter implements Writer<Field> {

  private static Writer<Type> typeWriter = new TypeWriter();

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

