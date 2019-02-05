
package com.github.havardh.javaflow.model;

import com.github.havardh.javaflow.annotations.JsonProperty;

public class ModelWithPropertyFieldAnnotation {

  @JsonProperty("annotatedFieldName")
  private String field;

  public String getField() {
    return field;
  }
}

