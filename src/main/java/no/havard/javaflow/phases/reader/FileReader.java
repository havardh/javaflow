package no.havard.javaflow.phases.reader;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class FileReader {

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

