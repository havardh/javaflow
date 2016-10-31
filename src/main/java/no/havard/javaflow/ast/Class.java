package no.havard.javaflow.ast;

import static java.util.Collections.emptyList;

import static no.havard.javaflow.util.Lists.union;

import java.util.List;
import java.util.Optional;

import no.havard.javaflow.model.CanonicalName;

public class Class extends Definition {

  private final Parent parent;
  private final List<Field> fields;

  public Class(CanonicalName name, Parent parent, List<Field> fields) {
    super(name);
    this.parent = parent;
    this.fields = fields;
  }

  public List<Field> getFields() {
    return union(getParentFields(), fields);
  }

  private List<Field> getParentFields() {
    return  getParent().map(Parent::getFields).orElse(emptyList());
  }

  public Optional<Parent> getParent() {
    return Optional.ofNullable(parent);
  }
}
