package com.github.havardh.javaflow.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ModelWithWildcardTypeParams {
  private List<?> wildcardList;
  private Map<?, Set<?>> wildcardMap;

  public List<?> getWildcardList() {
    return wildcardList;
  }

  public Map<?, Set<?>> getWildcardMap() {
    return wildcardMap;
  }
}
