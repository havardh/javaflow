package no.havard.javaflow.testutil;

import static no.havard.javaflow.model.fixtures.CanonicalNameFixtures.stringName;
import static no.havard.javaflow.testutil.MapConverter.builder;

public class MapConverterFixture {

  public static MapConverter.MapConverterBuilder stringMap() {
    return builder().with(stringName().build(), "string");
  }

}

