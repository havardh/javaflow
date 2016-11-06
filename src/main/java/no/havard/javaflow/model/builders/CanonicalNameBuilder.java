package no.havard.javaflow.model.builders;

import static no.havard.javaflow.model.CanonicalName.object;

import no.havard.javaflow.model.CanonicalName;

public final class CanonicalNameBuilder {
  private String packageName;
  private String name;

  private CanonicalNameBuilder() {
  }

  public static CanonicalNameBuilder canonicalName() {
    return new CanonicalNameBuilder();
  }

  public CanonicalNameBuilder withPackageName(String packageName) {
    this.packageName = packageName;
    return this;
  }

  public CanonicalNameBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public CanonicalName build() {
    return object(packageName, name);
  }
}

