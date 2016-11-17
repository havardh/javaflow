package no.havard.javaflow.definitions;

import static no.havard.javaflow.util.Maps.collect;
import static no.havard.javaflow.util.Maps.entry;

import java.util.Map;

public class Primitives {

  public static Map<String, String> PRIMITIVES = collect(
      entry("byte", "number"),
      entry("short", "number"),
      entry("int", "number"),
      entry("long", "number"),
      entry("float", "number"),
      entry("double", "number"),
      entry("boolean", "boolean"),
      entry("char", "string")
  );

  public static boolean isPrimitive(String name) {
    return PRIMITIVES.containsKey(name);
  }

  public static String get(String name) {
    return PRIMITIVES.get(name);
  }

}

