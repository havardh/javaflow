package com.github.havardh.javaflow.model;

import static java.lang.String.format;

import static com.github.havardh.javaflow.model.builders.CanonicalNameBuilder.canonicalName;

/**
 * A representation of a canonical name. This name should uniquely
 * identify a type. This class is used as the identifier for all
 * of the {@code Type} classes in JavaFlow.
 *
 * The {@code CanonicalName} consists of an optional package name and
 * a require name.
 */
public class CanonicalName {

  private final String packageName;
  private final String name;

  /**
   * Constructs a CanonicalName
   *
   * @param packageName - the package name
   * @param name        - the name
   */
  private CanonicalName(String packageName, String name) {
    this.packageName = packageName;
    this.name = name;
  }

  /**
   * Parses a CanonicalName from a string representation of
   * a canonical name.
   *
   * @param canonicalName canonical name as a string
   * @return a instance of {@code CanonicalName}
   */
  public static CanonicalName fromString(String canonicalName) {
    int lastDot = canonicalName.lastIndexOf('.');

    return canonicalName()
        .withPackageName(canonicalName.substring(0, lastDot))
        .withName(canonicalName.substring(lastDot+1, canonicalName.length()))
        .build();
  }

  /**
   * Factory for create a {@code CanonicalName} for a primitive type
   *
   * @param name the name of the primitive type
   * @return the {@code CanonicalName} for the primitive type
   */
  public static CanonicalName primitive(String name) {
    return new CanonicalName(null, name);
  }

  /**
   * Factory for create a {@code CanonicalName} for an object
   *
   * @param packageName the package name for the object
   * @param name the class name of the object
   * @return the {@code CanonicalName} for the object
   */
  public static CanonicalName object(String packageName, String name) {
    return new CanonicalName(packageName, name);
  }

  /**
   * Get the name of the CanonicalName
   *
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * Get the package name of the CanonicalName
   *
   * @return the package name
   */
  public String getPackageName() {
    return packageName;
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

    CanonicalName that = (CanonicalName) o;

    if (packageName != null ? !packageName.equals(that.packageName) : that.packageName != null) {
      return false;
    }
    return name != null ? name.equals(that.name) : that.name == null;

  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    int result = packageName != null ? packageName.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return packageName != null
        ? format("%s.%s", packageName, name)
        : name;
  }
}

