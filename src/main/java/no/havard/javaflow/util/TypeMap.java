package no.havard.javaflow.util;

import static java.util.Collections.emptyMap;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import com.esotericsoftware.yamlbeans.YamlReader;

public class TypeMap implements Map<String, String> {

  public static TypeMap emptyTypeMap() {
    return new TypeMap();
  }

  private Map<String, String> map = emptyMap();

  private TypeMap() {
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

  @Override
  public int size() {
    return map.size();
  }

  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return map.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return map.containsValue(value);
  }

  @Override
  public String get(Object key) {
    return map.get(key);
  }

  @Override
  public String put(String key, String value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public String remove(Object key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void putAll(Map<? extends String, ? extends String> m) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public Set<String> keySet() {
    return map.keySet();
  }

  @Override
  public Collection<String> values() {
    return map.values();
  }

  @Override
  public Set<Entry<String, String>> entrySet() {
    return map.entrySet();
  }
}

