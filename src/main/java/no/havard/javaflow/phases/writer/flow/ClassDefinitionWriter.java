package no.havard.javaflow.phases.writer.flow;

import no.havard.javaflow.model.ClassDefinition;
import no.havard.javaflow.phases.writer.Writer;

class ClassDefinitionWriter implements Writer<ClassDefinition> {
  @Override
  public void write(ClassDefinition definition) {
    System.out.println(definition.toString());
  }
}

