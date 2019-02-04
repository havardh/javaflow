package com.github.havardh.javaflow.model;

import com.github.havardh.javaflow.annotations.JsonIgnore;

public class ModelWithIgnoredField {
  @JsonIgnore
  private String field;

  @JsonIgnore
  public String getField() {
    return field;
  }
}

