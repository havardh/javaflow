package com.github.havardh.javaflow.model;

import javax.annotation.Nullable;

public class ModelWithNullableField {
  @Nullable
  private String field;

  @Nullable
  public String getField() {
    return field;
  }
}

