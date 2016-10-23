package no.havard.javaflow.model;

import java.util.Optional;

public abstract class Definition {
  protected final String name;
  protected final Optional<Parent> parent;

  public Definition(String name) {
    this.name = name;
    this.parent = Optional.empty();
  }

  public Definition(String name, Parent parent) {
    this.name = name;
    this.parent = Optional.of(parent);
  }

  public Optional<Parent> getParent() {
    return parent;
  }

  public void setParentRef(Definition definition) {
    parent.ifPresent(parent -> parent.setReference(definition));
  }

  public String getName() {
    return name;
  }
}

