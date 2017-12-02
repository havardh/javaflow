package com.github.havardh.javaflow.util;

import static java.util.Arrays.stream;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Static utilities for {@code Map}
 */
public class Maps {

  /**
   * Factory method for creating a AbstractMap.SimpleEntry
   *
   * @param key key of type {@code K}
   * @param value value of type {@code V}
   * @param <K> type for {@code key}
   * @param <V> type for {@code value}
   * @return a SimpleEntry
   */
  public static <K, V> Map.Entry<K, V> entry(K key, V value) {
    return new AbstractMap.SimpleEntry<>(key, value);
  }

  /**
   * Collect a list of entries to an unmodifiable map
   *
   * @param entries list of map entries
   * @param <K> type of map key
   * @param <V> type of map value
   * @return an unmodifiable map containing the entries
   */
  public static <K, V> Map<K, V> collect(Map.Entry<K, V>... entries) {
    return Collections.unmodifiableMap(stream(entries).collect(entriesToMap()));
  }

  private static <K, V> Collector<Map.Entry<K, V>, ?, Map<K, V>> entriesToMap() {
    return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
  }
}

