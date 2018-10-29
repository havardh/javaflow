package com.github.havardh.javaflow.model;

public class ModelWithFieldComments {
  // comment on field1
  private String field1;

  /* comment on field2 */
  private String field2;

  /** comment on field3 */
  private String field3;

  public String getField1() {
    return field1;
  }

  public String getField2() {
    return field2;
  }

  public String getField3() {
    return field3;
  }
}
