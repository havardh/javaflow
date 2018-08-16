package com.github.havardh.javaflow.ast.builders;

import com.github.havardh.javaflow.ast.List;
import com.github.havardh.javaflow.ast.Map;
import com.github.havardh.javaflow.model.CanonicalName;
import com.github.havardh.javaflow.ast.Type;

/**
 * Builder for {@code Type} objects
 */
public final class TypeBuilder {
  protected CanonicalName name;

  private Type firstType;
  private Type secondType;


  private TypeBuilder() {
  }

  /**
   * Create an empty {@code TypeBuilder}
   *
   * @return empty {@code TypeBuilder}
   */
  public static TypeBuilder type() {
    return new TypeBuilder();
  }

  /**
   * Factory method for creating a primitive type for the given name
   *
   * @param name the name of the type
   * @return a {@code Type} with the name
   */
  public static Type primitive(CanonicalName name) {
    return new Type(name);
  }

  /**
   * Factory method for creating an object type for the given name
   *
   * @param name the name of the type
   * @return a {@code Type} with the name
   */
  public static Type object(CanonicalName name) {
    return new Type(name);
  }

  /**
   * Factory method for creating a list type for the given name and value type name
   *
   * @param name the name of the type
   * @param type the type of the value
   * @return a {@code List} with the name and value type
   */
  public static Type list(CanonicalName name, Type type) {
    return new List(name, type);
  }

  /**
   * Factory method for creating a map type with a given name, key and value.
   *
   * @param name the name of the type
   * @param key the type of the keys in the map
   * @param value the type of the values in the map
   * @return the type for the map
   */
  public static Type map(CanonicalName name, Type key, Type value) {
    return new Map(name, key, value);
  }

  /**
   * Set the name of the builder
   *
   * @param name the name of the type
   * @return the builder for method chaining
   */
  public TypeBuilder withName(CanonicalName name) {
    this.name = name;
    return this;
  }

  /**
   * Set the type of a list type
   *
   * @param type the type of the list values
   * @return the builder for method chaining
   */
  public TypeBuilder withListType(Type type) {
    this.firstType = type;
    this.secondType = null;
    return this;
  }

  /**
   * Set the types of a map type
   *
   * @param key the type of the keys in the map
   * @param value the type of the values of the map
   * @return the builder for method chaining
   */
  public TypeBuilder withMapType(
      Type key,
      Type value
  ) {
    this.firstType = key;
    this.secondType = value;
    return this;
  }

  /**
   * Build a type with the current configuration
   *
   * @return the type
   */
  public Type build() {
    if (secondType != null) {
      return map(name, firstType, secondType);
    } else if (firstType != null) {
      return list(name, firstType);
    } else {
      return object(name);
    }
  }
}

