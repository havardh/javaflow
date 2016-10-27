package no.havard.javaflow.model;

import static java.lang.String.format;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

public class ClassDefinition extends Definition {

  private final List<FieldDefinition> fieldDefinitions;

  public ClassDefinition(CanonicalName name, List<FieldDefinition> definitions) {
    super(name);
    this.fieldDefinitions = definitions;
  }

  public ClassDefinition(
      CanonicalName name,
      CanonicalName parentName,
      List<FieldDefinition> definitions
  ) {
    super(name, new Parent(parentName));
    this.fieldDefinitions = definitions;
  }

  public List<FieldDefinition> getFieldDefinitions() {
    return union(getParentFieldDefinitions(), fieldDefinitions);
  }

  private List<FieldDefinition> getParentFieldDefinitions() {
    return  parent.map(p -> ((ClassDefinition)p.getReference()).getFieldDefinitions()).orElse(emptyList());
  }

  @Override
  public String toString() {
    return format("export type %s = {\n  %s,\n};", this.name, getFieldDefinitions().stream()
        .map(FieldDefinition::toString)
        .collect(joining(",\n  ")));
  }

  private static <T> List<T> union(List<T> a, List<T> b) {
    return Stream.concat(a.stream(), b.stream()).collect(toList());
  }
}
