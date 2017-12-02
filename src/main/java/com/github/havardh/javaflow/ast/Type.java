package com.github.havardh.javaflow.ast;

import static java.lang.String.format;

import com.github.havardh.javaflow.model.CanonicalName;

/**
 * The internal representation of a model
 */
public class Type {

  protected final CanonicalName name;

  /**
   * Create a {@code Type} with a given {@code CanonicalName}
   *
   * @param name the canonical name for the type
   */
  public Type(CanonicalName name) {
    this.name = name;
  }

  /**
   * Get the canonical name of type
   *
   * @return the canonical name of the type
   */
  public CanonicalName getCanonicalName() {
    return name;
  }

  /** {@inheritDoc} */
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

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return name != null ? name.hashCode() : 0;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return format("%s", name);
  }
}

