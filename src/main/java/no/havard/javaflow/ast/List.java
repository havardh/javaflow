package no.havard.javaflow.ast;

import no.havard.javaflow.model.CanonicalName;

public class List extends Type {
  private final CanonicalName type;

  public List(CanonicalName name, CanonicalName type) {
    super(name);
    this.type = type;
  }

  public CanonicalName getType() {
    return type;
  }
}

