package no.havard.javaflow.phases.writer.flow;

import no.havard.javaflow.model.ClassDefinition;
import no.havard.javaflow.model.Definition;
import no.havard.javaflow.model.EnumDefinition;
import no.havard.javaflow.phases.writer.Writer;

public class FlowWriter implements Writer<Definition> {

  private final ClassDefinitionWriter classDefinitionWriter = new ClassDefinitionWriter();
  private final EnumDefinitionWriter enumDefinitionWriter = new EnumDefinitionWriter();

  @Override
  public void write(Definition definition) {
    if (definition instanceof ClassDefinition) {
      classDefinitionWriter.write((ClassDefinition) definition);
    } else if (definition instanceof EnumDefinition) {
      enumDefinitionWriter.write((EnumDefinition) definition);
    }
    System.out.println();
  }

}
