package no.havard.javaflow.ast.builders;

import no.havard.javaflow.ast.List;
import no.havard.javaflow.ast.Map;
import no.havard.javaflow.model.CanonicalName;
import no.havard.javaflow.ast.Type;

public final class TypeBuilder {
  protected CanonicalName name;

  private CanonicalName firstName;
  private CanonicalName secondName;


  private TypeBuilder() {
  }

  public static TypeBuilder type() {
    return new TypeBuilder();
  }

  public static Type primitive(CanonicalName name) {
    return new Type(name);
  }

  public static Type object(CanonicalName name) {
    return new Type(name);
  }

  public static Type list(CanonicalName name, CanonicalName type) {
    return new List(name, type);
  }

  public static Type map(CanonicalName name, CanonicalName key, CanonicalName value) {
    return new Map(name, key, value);
  }

  public TypeBuilder withName(CanonicalName name) {
    this.name = name;
    return this;
  }

  public TypeBuilder withListType(CanonicalName name) {
    this.firstName = name;
    this.secondName = null;
    return this;
  }

  public TypeBuilder withMapType(
      CanonicalName firstName,
      CanonicalName secondName
  ) {
    this.firstName = firstName;
    this.secondName = secondName;
    return this;
  }

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

