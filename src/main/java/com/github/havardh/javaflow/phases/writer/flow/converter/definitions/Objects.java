package com.github.havardh.javaflow.phases.writer.flow.converter.definitions;

import java.util.Map;

import com.github.havardh.javaflow.util.Maps;

public class Objects {

  public static Map<String, String> OBJECTS = Maps.collect(
      Maps.entry("char[]", "string"),

      Maps.entry("java.util.Date", "string"),
      Maps.entry("java.util.Map", "Map"),
      Maps.entry("java.util.List", "Array"),
      Maps.entry("java.util.Collection", "Array"),
      Maps.entry("java.util.Set", "Array"),

      Maps.entry("java.lang.Boolean", "boolean"),
      Maps.entry("java.lang.Byte", "number"),
      Maps.entry("java.lang.Character", "string"),
      Maps.entry("java.lang.Double", "number"),
      Maps.entry("java.lang.Float", "number"),
      Maps.entry("java.lang.Integer", "number"),
      Maps.entry("java.lang.Long", "number"),
      Maps.entry("java.lang.Short", "number"),
      Maps.entry("java.lang.String", "string")
  );

  public static boolean isObject(String name) {
    return OBJECTS.containsKey(name);
  }

  public static String get(String name) {
    return OBJECTS.get(name);
  }

}

