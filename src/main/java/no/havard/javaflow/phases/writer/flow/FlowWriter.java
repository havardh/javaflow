package no.havard.javaflow.phases.writer.flow;

import java.io.IOException;

import no.havard.javaflow.model.ClassDefinition;
import no.havard.javaflow.model.Definition;
import no.havard.javaflow.model.EnumDefinition;
import no.havard.javaflow.phases.writer.Writer;

public class FlowWriter implements Writer<Definition> {

  private final ClassDefinitionWriter classDefinitionWriter = new ClassDefinitionWriter();
  private final EnumDefinitionWriter enumDefinitionWriter = new EnumDefinitionWriter();

  @Override
  public void write(Definition definition, java.io.Writer writer) throws IOException {
    if (definition instanceof ClassDefinition) {
      classDefinitionWriter.write((ClassDefinition) definition, writer);
    } else if (definition instanceof EnumDefinition) {
      enumDefinitionWriter.write((EnumDefinition) definition, writer);
    }
    writer.write("\n\n");
  }

}
