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
}

