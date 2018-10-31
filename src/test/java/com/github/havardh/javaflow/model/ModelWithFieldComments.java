package com.github.havardh.javaflow.model;

import javax.annotation.Generated;

public class ModelWithFieldComments {
  // comment on field1
  private String field1;

  /* comment on field2 */
  private String field2;

  /** comment on field3 */
  private String field3;

  @Generated("to test dangling javadoc comments")
  /** comment on field4 */
  private String field4;

  public String getField1() {
    return field1;
  }

  public String getField2() {
    return field2;
  }

  public String getField3() {
    return field3;
  }

  public String getField4() {
    return field4;
  }
}
