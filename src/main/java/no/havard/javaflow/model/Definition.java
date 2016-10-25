package no.havard.javaflow.model;

import static java.lang.String.format;

import java.util.Optional;

public abstract class Definition {
  private String packageName;
  protected final String name;
  protected final Optional<Parent> parent;

  public Definition(String packageName, String name) {
    this.packageName = packageName;
    this.name = name;
    this.parent = Optional.empty();
  }

  public Definition(String packageName, String name, Parent parent) {
    this.packageName = packageName;
    this.name = name;
    this.parent = Optional.of(parent);
  }

  public String getPackageName() {
    return packageName;
  }

  public String getCanonicalName() {
    return format("%s.%s", packageName, name);
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

