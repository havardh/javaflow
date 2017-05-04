package com.github.havardh.javaflow.testutil;

import static com.github.havardh.javaflow.testutil.MapConverter.builder;

import com.github.havardh.javaflow.model.fixtures.CanonicalNameFixtures;

public class MapConverterFixture {

  public static MapConverter.MapConverterBuilder stringMap() {
    return builder().with(CanonicalNameFixtures.stringName().build(), "string");
  }

}

