package no.havard.javaflow.model.builders;

import static no.havard.javaflow.model.Type.list;
import static no.havard.javaflow.model.Type.map;
import static no.havard.javaflow.model.Type.object;

import no.havard.javaflow.model.CanonicalName;
import no.havard.javaflow.model.Type;

public final class TypeBuilder {
  protected CanonicalName name;

  private CanonicalName firstName;
  private CanonicalName secondName;


  private TypeBuilder() {
  }

  public static TypeBuilder type() {
    return new TypeBuilder();
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

