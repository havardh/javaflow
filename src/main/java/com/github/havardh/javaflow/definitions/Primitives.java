package com.github.havardh.javaflow.definitions;

import java.util.Map;

import com.github.havardh.javaflow.util.Maps;

public class Primitives {

  public static Map<String, String> PRIMITIVES = Maps.collect(
      Maps.entry("byte", "number"),
      Maps.entry("short", "number"),
      Maps.entry("int", "number"),
      Maps.entry("long", "number"),
      Maps.entry("float", "number"),
      Maps.entry("double", "number"),
      Maps.entry("boolean", "boolean"),
      Maps.entry("char", "string")
  );

  public static boolean isPrimitive(String name) {
    return PRIMITIVES.containsKey(name);
  }

  public static String get(String name) {
    return PRIMITIVES.get(name);
  }

}

