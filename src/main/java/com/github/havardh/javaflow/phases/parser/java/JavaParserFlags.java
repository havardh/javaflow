package com.github.havardh.javaflow.phases.parser.java;

public class JavaParserFlags {
  private boolean includeStaticFields;

  private JavaParserFlags(boolean includeStaticFields) {
    this.includeStaticFields = includeStaticFields;
  }

  public static JavaParserFlags defaultFlags() {
    return Config.withDefaults().build();
  }

  public boolean shouldIncludeStaticFields() {
    return includeStaticFields;
  }

  public static Config config() {
    return new Config();
  }

  public static class Config {

    private boolean includeStaticFields;

    public static Config emptyConfig() {
      return new Config();
    }

    public static Config withDefaults() {
      return new Config().includeStaticFields(false);
    }

    public Config includeStaticFields(boolean includeStaticFields) {
      this.includeStaticFields = includeStaticFields;
      return this;
    }

    public JavaParserFlags build() {
      return new JavaParserFlags(includeStaticFields);
    }
  }
}
