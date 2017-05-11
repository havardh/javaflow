package com.github.havardh.javaflow.ast;

import java.util.List;

import com.github.havardh.javaflow.model.CanonicalName;

/**
 * Internal representation of enum types
 */
public class Enum extends Type {

  private final List<String> values;

  /**
   * Create a {@code Enum} with a name and a list of allowed values
   *
   * @param name the name
   * @param values the list of allowed values
   */
  public Enum(CanonicalName name, List<String> values) {
    super(name);
    this.values = values;
  }

  /**
   * Get the list of values
   *
   * @return the list of values
   */
  public List<String> getValues() {
    return values;
  }
}
