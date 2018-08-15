package com.github.havardh.javaflow.model;

import java.util.List;
import java.util.Map;

public class ModelWithMapGenericValue {
  private Map<String, List<Integer>> field;

  public Map<String, List<Integer>> getField() {
    return field;
  }
}
