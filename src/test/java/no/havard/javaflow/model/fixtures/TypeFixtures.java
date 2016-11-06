package no.havard.javaflow.model.fixtures;

import static no.havard.javaflow.model.builders.CanonicalNameBuilder.canonicalName;
import static no.havard.javaflow.ast.builders.TypeBuilder.type;
import static no.havard.javaflow.model.fixtures.CanonicalNameFixtures.stringName;

import no.havard.javaflow.ast.builders.TypeBuilder;

public class TypeFixtures {

  public static TypeBuilder stringType() {
    return type()
        .withName(stringName().build());
  }

  public static TypeBuilder listType() {
    return type()
        .withName(canonicalName()
            .withName("List")
            .withPackageName("java.util")
            .build())
        .withListType(stringName().build());
  }



  public static TypeBuilder mapType() {
    return type()
        .withName(canonicalName()
          .withName("Map")
          .withPackageName("java.util")
          .build())
        .withMapType(
            stringName().build(),
            stringName().build()
        );
  }

}

