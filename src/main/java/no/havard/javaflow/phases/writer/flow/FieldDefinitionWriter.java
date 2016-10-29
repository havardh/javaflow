package no.havard.javaflow.phases.writer.flow;

import java.io.IOException;

import no.havard.javaflow.model.FieldDefinition;
import no.havard.javaflow.model.Type;
import no.havard.javaflow.phases.writer.Writer;

public class FieldDefinitionWriter implements Writer<FieldDefinition> {

  private static Writer<Type> typeWriter = new TypeWriter();

  @Override
  public void write(FieldDefinition fieldDefinition, java.io.Writer writer) throws IOException {
    writer.write(fieldDefinition.getName());
    writer.write(": ");
    if (fieldDefinition.isNullable()) {
      writer.write("?");
    }
    typeWriter.write(fieldDefinition.getType(), writer);
  }
}

