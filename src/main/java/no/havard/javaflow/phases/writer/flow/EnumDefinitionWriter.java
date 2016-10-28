package no.havard.javaflow.phases.writer.flow;

import no.havard.javaflow.model.EnumDefinition;
import no.havard.javaflow.phases.writer.Writer;

class EnumDefinitionWriter implements Writer<EnumDefinition> {
  @Override
  public void write(EnumDefinition enumDefinition) {
    System.out.println(enumDefinition);
  }
}

