package com.github.havardh.javaflow.model.builders;

import com.github.havardh.javaflow.model.CanonicalName;

/**
 * A builder for the type {@code CanonicalName}.
 */
public final class CanonicalNameBuilder {
  private String packageName;
  private String name;

  private CanonicalNameBuilder() {
  }

  /**
   * Create a empty builder instance
   * @return - a canonical name builder
   */
  public static CanonicalNameBuilder canonicalName() {
    return new CanonicalNameBuilder();
  }

  /**
   * Sets the package name on the builder
   *
   * @param packageName - the package name
   * @return - the builder itself
   */
  public CanonicalNameBuilder withPackageName(String packageName) {
    this.packageName = packageName;
    return this;
  }

  /**
   * Sets the name on the builder
   *
   * @param name - the class name
   * @return - the builder itself
   */
  public CanonicalNameBuilder withName(String name) {
    this.name = name;
    return this;
  }

  /**
   * Builds an instance of a {@code CanonicalName}.
   *
   * @return - a instance of {@code CanonicalName}
   */
  public CanonicalName build() {
    return CanonicalName.object(packageName, name);
  }
}

