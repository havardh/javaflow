package no.havard.javaflow.definitions;

import static no.havard.javaflow.util.Maps.collect;
import static no.havard.javaflow.util.Maps.entry;

import java.util.Map;

public class Objects {

  public static Map<String, String> OBJECTS = collect(
      entry("char[]", "string"),

      entry("java.util.Date", "string"),
      entry("java.util.Map", "Map"),
      entry("java.util.List", "Array"),
      entry("java.util.Collection", "Array"),

      entry("java.lang.Boolean", "boolean"),
      entry("java.lang.Byte", "number"),
      entry("java.lang.Character", "string"),
      entry("java.lang.Double", "number"),
      entry("java.lang.Float", "number"),
      entry("java.lang.Integer", "number"),
      entry("java.lang.Long", "number"),
      entry("java.lang.Short", "number"),
      entry("java.lang.String", "string")
  );

  public static boolean isObject(String name) {
    return OBJECTS.containsKey(name);
  }

  public static String get(String name) {
    return OBJECTS.get(name);
  }

}

