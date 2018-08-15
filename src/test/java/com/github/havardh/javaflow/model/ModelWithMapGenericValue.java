package com.github.havardh.javaflow.model;

import java.util.List;
import java.util.Map;

public class ModelWithMapGenericValue {
  private Map<String, List<Integer>> field;
  private Map<String, List<List<Map<String, Integer>>>> multipleNestedField;

  public Map<String, List<Integer>> getField() {
    return field;
  }
  public Map<String, List<List<Map<String, Integer>>>> getMultipleNestedField() {
    return multipleNestedField;
  }
}
