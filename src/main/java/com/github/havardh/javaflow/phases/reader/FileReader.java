package com.github.havardh.javaflow.phases.reader;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Reader to read a single file into a string
 */
public class FileReader {

  /**
   * Reads a single file into an Optional {@code String}.
   *
   * @param filename the name of the file to read
   * @return an Optional {@code String} with the file content
   */
  public Optional<String> read(String filename) {
    try {
      return of(new String(Files.readAllBytes(Paths.get(filename))));
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
    return empty();
  }

}

