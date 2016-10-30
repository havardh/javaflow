package no.havard.javaflow.phases.writer.flow;

import java.io.IOException;

import no.havard.javaflow.model.Class;
import no.havard.javaflow.model.Field;
import no.havard.javaflow.phases.writer.Writer;

class ClassWriter implements Writer<Class> {

  private static Writer<Field> fieldWriter = new FieldDefinitionWriter();

  @Override
  public void write(Class definition, java.io.Writer writer) throws IOException {
    writer.write("export type ");
    writer.write(definition.getName());
    writer.write(" = {");
    writeFields(definition, writer);
    writer.write("};");
  }

  private void writeFields(Class definition, java.io.Writer writer) throws IOException {
    if (definition.getFields().size() > 0) {
      writer.write("\n");
    }
    for (Field field : definition.getFields()) {
      writer.write("  ");
      fieldWriter.write(field, writer);
      writer.write(",\n");
    }
  }
}

