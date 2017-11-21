package com.github.havardh.javaflow.model;

import static java.lang.String.format;

import static com.github.havardh.javaflow.model.builders.CanonicalNameBuilder.canonicalName;

public class CanonicalName {

  private final String packageName;
  private final String name;

  private CanonicalName(String packageName, String name) {
    this.packageName = packageName;
    this.name = name;
  }

  public static CanonicalName fromString(String canonicalName) {
    int lastDot = canonicalName.lastIndexOf('.');

    return canonicalName()
        .withPackageName(canonicalName.substring(0, lastDot))
        .withName(canonicalName.substring(lastDot+1, canonicalName.length()))
        .build();
  }

  public static CanonicalName primitive(String name) {
    return new CanonicalName(null, name);
  }

  public static CanonicalName object(String packageName, String name) {
    return new CanonicalName(packageName, name);
  }

  public String getName() {
    return name;
  }

  public String getPackageName() {
    return packageName;
  }

  public String getCanonicalName() {
    return packageName != null
        ? format("%s.%s", packageName, name)
        : name;
  }

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

  @Override
  public int hashCode() {
    int result = packageName != null ? packageName.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return getCanonicalName();
  }
}

