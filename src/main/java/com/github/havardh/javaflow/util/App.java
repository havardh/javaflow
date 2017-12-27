package com.github.havardh.javaflow.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class App {

  private static final Properties properties = new Properties();

  static {
    try (InputStream inputStream = App.class.getResourceAsStream("/version.properties")) {

      properties.load(inputStream);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String version() {
    return properties.getProperty("version", "no-version");
  }

}
