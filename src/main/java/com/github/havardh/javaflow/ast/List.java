package com.github.havardh.javaflow.ast;

import com.github.havardh.javaflow.model.CanonicalName;

/**
 * Internal representation of a list
 */
public class List extends Type {
  private final Type type;

  /**
   * Create a {@code List} with a name and a type for the values
   *
   * @param name the name of this type
   * @param type the type of the value
   */
  public List(CanonicalName name, Type type) {
    super(name);
    this.type = type;
  }

  /**
   * Get the type of the values
   *
   * @return the type of the value
   */
  public Type getType() {
    return type;
  }
}

