package com.github.havardh.javaflow.model;

import java.util.List;
import java.util.Map;

public class ModelWithGenericMapKey {
  private Map<List<Integer>, Integer> field;

  public Map<List<Integer>, Integer> getField() {
    return field;
  }
}
