package no.havard.javaflow.ast;

import no.havard.javaflow.model.CanonicalName;

public abstract class Definition {
  protected final CanonicalName name;

  public Definition(CanonicalName name) {
    this.name = name;
  }

  public String getPackageName() {
    return name.getPackageName();
  }

  public String getCanonicalName() {
    return name.getCanonicalName();
  }

  public String getName() {
    return name.getName();
  }
}

