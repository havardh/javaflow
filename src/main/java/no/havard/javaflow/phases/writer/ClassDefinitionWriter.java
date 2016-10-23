package no.havard.javaflow.phases.writer;

import no.havard.javaflow.model.ClassDefinition;

class ClassDefinitionWriter implements Writer<ClassDefinition> {
  @Override
  public void write(ClassDefinition definition) {
    System.out.println(definition.toString());
  }
}

