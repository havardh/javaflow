package no.havard.javaflow.phases.writer;

import no.havard.javaflow.model.EnumDefinition;

class EnumDefinitionWriter implements Writer<EnumDefinition> {
  @Override
  public void write(EnumDefinition enumDefinition) {
    System.out.println(enumDefinition);
  }
}

