package com.github.havardh.javaflow.phases.parser.java;

import static java.util.Collections.emptyMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import com.github.havardh.javaflow.model.CanonicalName;
import com.github.havardh.javaflow.util.Maps;

public class CanonicalNameFactoryTest {

  private static final String PACKAGE_NAME = "com.github.havardh";

  @Test
  public void shouldFindAllJavaLangTypes() {
    Map<String, String> imports = emptyMap();

    CanonicalNameFactory factory = new CanonicalNameFactory(PACKAGE_NAME, imports);

    assertThat(factory.build("Boolean"), Matchers.is(CanonicalName.fromString("java.lang.Boolean")));
    assertThat(factory.build("Byte"), Matchers.is(CanonicalName.fromString("java.lang.Byte")));
    assertThat(factory.build("Character"), Matchers.is(CanonicalName.fromString("java.lang.Character")));
    assertThat(factory.build("Double"), Matchers.is(CanonicalName.fromString("java.lang.Double")));
    assertThat(factory.build("Float"), Matchers.is(CanonicalName.fromString("java.lang.Float")));
    assertThat(factory.build("Integer"), Matchers.is(CanonicalName.fromString("java.lang.Integer")));
    assertThat(factory.build("Long"), Matchers.is(CanonicalName.fromString("java.lang.Long")));
    assertThat(factory.build("Short"), Matchers.is(CanonicalName.fromString("java.lang.Short")));
    assertThat(factory.build("String"), Matchers.is(CanonicalName.fromString("java.lang.String")));
  }

  @Test
  public void shouldFindJavaTypesInImports() {
    Map<String, String> imports = Maps.collect(Maps.entry("MyType", "com.github.havardh.mypackage"));

    CanonicalNameFactory factory = new CanonicalNameFactory(PACKAGE_NAME, imports);

    assertThat(factory.build("MyType"), Matchers.is(CanonicalName.fromString("com.github.havardh.mypackage.MyType")));
  }

  @Test
  public void shouldPlaceTypesNotFoundInImportOrLangPackageInGivenPackage() {
    Map<String, String> imports = emptyMap();

    CanonicalNameFactory factory = new CanonicalNameFactory(PACKAGE_NAME, imports);

    assertThat(factory.build("AType"), Matchers.is(CanonicalName.fromString(PACKAGE_NAME + ".AType")));
  }

}
