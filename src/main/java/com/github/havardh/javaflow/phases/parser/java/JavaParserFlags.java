package com.github.havardh.javaflow.phases.parser.java;

public class JavaParserFlags {
  private boolean includeStaticFields;

  private JavaParserFlags(boolean includeStaticFields) {
    this.includeStaticFields = includeStaticFields;
  }

  public static JavaParserFlags defaultFlags() {
    return Builder.withDefaults().build();
  }

  boolean shouldIncludeStaticFields() {
    return includeStaticFields;
  }

  public static Builder flagsBuilder() {
    return new Builder();
  }

  public static class Builder {

    private boolean includeStaticFields;

    static Builder withDefaults() {
      return new Builder().includeStaticFields(false);
    }

    public Builder includeStaticFields(boolean includeStaticFields) {
      this.includeStaticFields = includeStaticFields;
      return this;
    }

    public JavaParserFlags build() {
      return new JavaParserFlags(includeStaticFields);
    }
  }
}
