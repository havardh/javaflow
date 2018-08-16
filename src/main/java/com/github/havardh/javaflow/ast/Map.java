package com.github.havardh.javaflow.ast;

import com.github.havardh.javaflow.model.CanonicalName;

/**
 * Internal representation of a key value map.
 */
public class Map extends Type {
  private final Type key;
  private final Type value;

  /**
   * Create a {@code Map} with a name and a type for the key
   * and for the value
   *
   * @param name the name of this type
   * @param key the type of the key
   * @param value the type of the value
   */
  public Map(CanonicalName name, Type key, Type value) {
    super(name);
    this.key = key;
    this.value = value;
  }

  /**
   * Get the type of the keys
   *
   * @return the type of the key
   */
  public Type getKeyType() {
    return key;
  }

  /**
   * Get the type of the values
   *
   * @return the type of the value
   */
  public Type getValueType() {
    return value;
  }
}

