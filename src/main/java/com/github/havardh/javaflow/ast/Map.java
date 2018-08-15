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
   * @param key the name of the key type
   * @param value the name of the value type
   */
  public Map(CanonicalName name, Type key, Type value) {
    super(name);
    this.key = key;
    this.value = value;
  }

  /**
   * Get the name of the type for the keys
   *
   * @return the name of the key type
   */
  public Type getKeyType() {
    return key;
  }

  /**
   * Get the name of the type for the values
   *
   * @return the name of the value type
   */
  public Type getValueType() {
    return value;
  }
}

