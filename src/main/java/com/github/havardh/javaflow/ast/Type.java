package com.github.havardh.javaflow.ast;

import static java.lang.String.format;

import com.github.havardh.javaflow.model.CanonicalName;

public class Type {

  protected final CanonicalName name;

  public Type(CanonicalName name) {
    this.name = name;
  }

  public CanonicalName getCanonicalName() {
    return name;
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

