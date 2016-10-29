package no.havard.javaflow.phases.writer.flow;

import java.io.IOException;

import no.havard.javaflow.model.ClassDefinition;
import no.havard.javaflow.model.FieldDefinition;
import no.havard.javaflow.phases.writer.Writer;

class ClassDefinitionWriter implements Writer<ClassDefinition> {

  private static Writer<FieldDefinition> fieldWriter = new FieldDefinitionWriter();

  @Override
  public void write(ClassDefinition definition, java.io.Writer writer) throws IOException {
    writer.write("export type ");
    writer.write(definition.getName());
    writer.write(" = {");
    writeFields(definition, writer);
    writer.write("};");
  }

  private void writeFields(ClassDefinition definition, java.io.Writer writer) throws IOException {
    if (definition.getFieldDefinitions().size() > 0) {
      writer.write("\n");
    }
    for (FieldDefinition field : definition.getFieldDefinitions()) {
      writer.write("  ");
      fieldWriter.write(field, writer);
      writer.write(",\n");
    }
  }
}

