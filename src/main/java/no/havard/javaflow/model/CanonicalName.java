package no.havard.javaflow.model;

import static java.lang.String.format;

public class CanonicalName {

  private final String packageName;
  private final String name;

  public CanonicalName(String name) {
    this.packageName = null;
    this.name = name;
  }

  public CanonicalName(String packageName, String name) {
    this.packageName = packageName;
    this.name = name;
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
  public String toString() {
    return name;
  }
}

