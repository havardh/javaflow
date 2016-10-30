package no.havard.javaflow.model;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

import static no.havard.javaflow.util.Lists.union;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Class extends Definition {

  private final Parent parent;
  private final List<Field> fields;

  public Class(CanonicalName name, List<Field> definitions) {
    super(name);
    this.parent = null;
    this.fields = definitions;
  }

  public Class(
      CanonicalName name,
      CanonicalName parentName,
      List<Field> definitions
  ) {
    super(name);
    this.parent = new Parent(parentName);
    this.fields = definitions;
  }

  public List<Field> getFields() {
    return union(getParentFieldDefinitions(), fields);
  }

  private List<Field> getParentFieldDefinitions() {
    return  getParent().map(p -> (p.getReference()).getFields()).orElse(emptyList());
  }

  public Optional<Parent> getParent() {
    return Optional.ofNullable(parent);
  }

  public void setParentRef(Class aClass) {
    getParent().ifPresent(parent -> parent.setReference(aClass));
  }
}
