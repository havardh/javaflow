package no.havard.javaflow.model;

import java.util.Optional;

public abstract class Definition {
  protected final CanonicalName name;
  protected final Optional<Parent> parent;

  public Definition(CanonicalName name) {
    this.name = name;
    this.parent = Optional.empty();
  }

  public Definition(CanonicalName name, Parent parent) {
    this.name = name;
    this.parent = Optional.of(parent);
  }

  public String getPackageName() {
    return name.getPackageName();
  }

  public String getCanonicalName() {
    return name.getCanonicalName();
  }

  public Optional<Parent> getParent() {
    return parent;
  }

  public void setParentRef(Definition definition) {
    parent.ifPresent(parent -> parent.setReference(definition));
  }

  public String getName() {
    return name.getName();
  }
}

