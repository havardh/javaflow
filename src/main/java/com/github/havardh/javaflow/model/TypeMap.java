package com.github.havardh.javaflow.model;

import static java.util.Collections.emptyMap;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.esotericsoftware.yamlbeans.YamlReader;

/**
 * Type map class to contain the map from source language types to
 * flow types.
 */
public class TypeMap implements Map<String, String> {

  /**
   * Factory method to create an empty {@code TypeMap}
   *
   * @return empty {@code TypeMap}
   */
  public static TypeMap emptyTypeMap() {
    return new TypeMap(emptyMap());
  }

  private Map<String, String> map;

  /**
   * Create a {@code TypeMap} based on the given {@code Map}
   *
   * @param map the map to create the {@code TypeMap} from.
   */
  public TypeMap(Map<String, String> map) {
    this.map = map;
  }

  public TypeMap(String filename) {
    try (FileReader fileReader = new FileReader(filename)) {
      YamlReader yamlReader = new YamlReader(fileReader);

      map = (Map<String, String>)yamlReader.read();
    } catch (FileNotFoundException e) {
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(0);
    }
  }

  /** {@inheritDoc} */
  @Override
  public int size() {
    return map.size();
  }

  /** {@inheritDoc} */
  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  /** {@inheritDoc} */
  @Override
  public boolean containsKey(Object key) {
    return map.containsKey(key);
  }

  /** {@inheritDoc} */
  @Override
  public boolean containsValue(Object value) {
    return map.containsValue(value);
  }

  /** {@inheritDoc} */
  @Override
  public String get(Object key) {
    return map.get(key);
  }

  /**
   * Unsupported operation
   *
   * @throws UnsupportedOperationException always thrown
   */
  @Override
  public String put(String key, String value) {
    throw new UnsupportedOperationException();
  }

  /**
   * Unsupported operation
   *
   * @throws UnsupportedOperationException always thrown
   */
  @Override
  public String remove(Object key) {
    throw new UnsupportedOperationException();
  }

  /**
   * Unsupported operation
   *
   * @throws UnsupportedOperationException always thrown
   */
  @Override
  public void putAll(Map<? extends String, ? extends String> m) {
    throw new UnsupportedOperationException();
  }

  /**
   * Unsupported operation
   *
   * @throws UnsupportedOperationException always thrown
   */
  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  /** {@inheritDoc} */
  @Override
  public Set<String> keySet() {
    return map.keySet();
  }

  /** {@inheritDoc} */
  @Override
  public Collection<String> values() {
    return map.values();
  }

  /** {@inheritDoc} */
  @Override
  public Set<Entry<String, String>> entrySet() {
    return map.entrySet();
  }
}

