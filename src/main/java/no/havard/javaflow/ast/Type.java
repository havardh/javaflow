package no.havard.javaflow.ast;

import static java.lang.String.format;

import no.havard.javaflow.model.CanonicalName;

public class Type {

  protected final CanonicalName name;

  public Type(CanonicalName name) {
    this.name = name;
  }

  public String getName() {
    return name.getName();
  }

  public String getFullName() {
    return name.getCanonicalName();
  }

  public CanonicalName getCanonicalName() {
    return name;
  }

  public String getPackageName() {
    return name.getPackageName();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Type type = (Type) o;

    return name != null ? name.equals(type.name) : type.name == null;
  }

  @Override
  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }

  @Override
  public String toString() {
    return format("%s", name);
  }
}

