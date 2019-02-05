package com.github.havardh.javaflow.annotations;

public @interface JsonProperty {

  public final static String USE_DEFAULT_NAME = "";

  String value() default USE_DEFAULT_NAME;

  boolean require() default false;

}
