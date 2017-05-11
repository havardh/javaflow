package com.github.havardh.javaflow.ast;

import com.github.havardh.javaflow.model.CanonicalName;

/**
 * Internal representation of a list
 */
public class List extends Type {
  private final CanonicalName type;

  /**
   * Create a {@code List} with a name and a type for the values
   *
   * @param name the name of this type
   * @param type the name of the value type
   */
  public List(CanonicalName name, CanonicalName type) {
    super(name);
    this.type = type;
  }

  /**
   * Get the name of the type for the values
   *
   * @return the name of type for the value
   */
  public CanonicalName getType() {
    return type;
  }
}

