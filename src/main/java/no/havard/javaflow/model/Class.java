package no.havard.javaflow.model;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

public class Class extends Definition {

  private final List<Field> fields;

  public Class(CanonicalName name, List<Field> definitions) {
    super(name);
    this.fields = definitions;
  }

  public Class(
      CanonicalName name,
      CanonicalName parentName,
      List<Field> definitions
  ) {
    super(name, new Parent(parentName));
    this.fields = definitions;
  }

  public List<Field> getFields() {
    return union(getParentFieldDefinitions(), fields);
  }

  private List<Field> getParentFieldDefinitions() {
    return  parent.map(p -> ((Class)p.getReference()).getFields()).orElse(emptyList());
  }

  private static <T> List<T> union(List<T> a, List<T> b) {
    return Stream.concat(a.stream(), b.stream()).collect(toList());
  }
}
