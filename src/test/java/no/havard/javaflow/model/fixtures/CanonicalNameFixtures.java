package no.havard.javaflow.model.fixtures;

import static no.havard.javaflow.model.builders.CanonicalNameBuilder.canonicalName;

import no.havard.javaflow.model.builders.CanonicalNameBuilder;

public class CanonicalNameFixtures {

  public static CanonicalNameBuilder stringName() {
    return canonicalName()
        .withName("String")
        .withPackageName("java.lang");
  }
}

