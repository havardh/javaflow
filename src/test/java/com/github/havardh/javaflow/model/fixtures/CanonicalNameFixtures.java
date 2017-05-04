package com.github.havardh.javaflow.model.fixtures;

import static com.github.havardh.javaflow.model.builders.CanonicalNameBuilder.canonicalName;

import com.github.havardh.javaflow.model.builders.CanonicalNameBuilder;

public class CanonicalNameFixtures {

  public static CanonicalNameBuilder stringName() {
    return canonicalName()
        .withName("String")
        .withPackageName("java.lang");
  }
}

