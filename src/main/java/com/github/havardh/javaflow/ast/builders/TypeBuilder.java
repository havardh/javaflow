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

  private CanonicalName firstName;
  private CanonicalName secondName;


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
   * @param type the name of the value type
   * @return a {@code List} with the name and value type
   */
  public static Type list(CanonicalName name, CanonicalName type) {
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
  public static Type map(CanonicalName name, CanonicalName key, CanonicalName value) {
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
   * @param name the type of the list values
   * @return the builder for method chaining
   */
  public TypeBuilder withListType(CanonicalName name) {
    this.firstName = name;
    this.secondName = null;
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
      CanonicalName key,
      CanonicalName value
  ) {
    this.firstName = key;
    this.secondName = value;
    return this;
  }

  /**
   * Build a type with the current configuration
   *
   * @return the type
   */
  public Type build() {
    if (secondName != null) {
      return map(name, firstName, secondName);
    } else if (firstName != null) {
      return list(name, firstName);
    } else {
      return object(name);
    }
  }
}

