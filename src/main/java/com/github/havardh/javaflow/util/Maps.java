package com.github.havardh.javaflow.util;

import static java.util.Arrays.stream;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Maps {

  public static <K, V> Map.Entry<K, V> entry(K key, V value) {
    return new AbstractMap.SimpleEntry<>(key, value);
  }

  public static <K, V> Map<K, V> collect(Map.Entry<K, V>... entries) {
    return Collections.unmodifiableMap(stream(entries).collect(entriesToMap()));
  }

  private static <K, V> Collector<Map.Entry<K, V>, ?, Map<K, V>> entriesToMap() {
    return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
  }
}

