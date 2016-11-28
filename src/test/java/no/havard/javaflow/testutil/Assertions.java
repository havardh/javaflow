package no.havard.javaflow.testutil;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final class Assertions {

  private Assertions() {
  }

  public static void assertStringEqual(String actual, String... expected) {
    assertEquals(join(expected), actual);
  }

  private static String join(String ...expected) {
    return stream(expected).collect(joining("\n"));
  }
}

