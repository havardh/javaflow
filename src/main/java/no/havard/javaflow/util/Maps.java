package no.havard.javaflow.util;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Maps {

  public static Map.Entry<String, String> entry(String key, String value) {
    return new AbstractMap.SimpleEntry<>(key, value);
  }

  public static Collector<Map.Entry<String, String>, ?, Map<String, String>> entriesToMap() {
    return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
  }
}

