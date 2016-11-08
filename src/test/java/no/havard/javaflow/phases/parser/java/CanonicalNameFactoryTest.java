package no.havard.javaflow.phases.parser.java;

import static java.util.Collections.emptyMap;

import static no.havard.javaflow.model.CanonicalName.fromString;
import static no.havard.javaflow.util.Maps.collect;
import static no.havard.javaflow.util.Maps.entry;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class CanonicalNameFactoryTest {

  private static final String PACKAGE_NAME = "no.havard";

  @Test
  public void shouldFindAllJavaLangTypes() {
    Map<String, String> imports = emptyMap();

    CanonicalNameFactory factory = new CanonicalNameFactory(PACKAGE_NAME, imports);

    assertThat(factory.build("Boolean"), is(fromString("java.lang.Boolean")));
    assertThat(factory.build("Byte"), is(fromString("java.lang.Byte")));
    assertThat(factory.build("Character"), is(fromString("java.lang.Character")));
    assertThat(factory.build("Double"), is(fromString("java.lang.Double")));
    assertThat(factory.build("Float"), is(fromString("java.lang.Float")));
    assertThat(factory.build("Integer"), is(fromString("java.lang.Integer")));
    assertThat(factory.build("Long"), is(fromString("java.lang.Long")));
    assertThat(factory.build("Short"), is(fromString("java.lang.Short")));
    assertThat(factory.build("String"), is(fromString("java.lang.String")));
  }

  @Test
  public void shouldFindJavaTypesInImports() {
    Map<String, String> imports = collect(entry("MyType", "no.havard.mypackage"));

    CanonicalNameFactory factory = new CanonicalNameFactory(PACKAGE_NAME, imports);

    assertThat(factory.build("MyType"), is(fromString("no.havard.mypackage.MyType")));
  }

  @Test
  public void shouldPlaceTypesNotFoundInImportOrLangPackageInGivenPackage() {
    Map<String, String> imports = emptyMap();

    CanonicalNameFactory factory = new CanonicalNameFactory(PACKAGE_NAME, imports);

    assertThat(factory.build("AType"), is(fromString(PACKAGE_NAME + ".AType")));
  }

}
