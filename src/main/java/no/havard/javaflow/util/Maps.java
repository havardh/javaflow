package no.havard.javaflow.util;

import static java.util.Arrays.stream;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Maps {

  public static Map.Entry<String, String> entry(String key, String value) {
    return new AbstractMap.SimpleEntry<>(key, value);
  }

  public static Map<String, String> collect(Map.Entry<String, String>... entries) {
    return Collections.unmodifiableMap(stream(entries).collect(entriesToMap()));
  }

  public static Collector<Map.Entry<String, String>, ?, Map<String, String>> entriesToMap() {
    return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
  }
}

