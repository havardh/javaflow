package no.havard.javaflow.model;

import java.util.Optional;

public class Definition {
  protected final String name;
  protected final Optional<String> parent;

  public Definition(String name) {
    this.name = name;
    this.parent = Optional.empty();
  }

  public Definition(String name, String parent) {
    this.name = name;
    this.parent = Optional.of(parent);
  }

  public Optional<String> getParent() {
    return parent;
  }

  public String getName() {
    return name;
  }
}

