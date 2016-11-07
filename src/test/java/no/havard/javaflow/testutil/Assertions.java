package no.havard.javaflow.testutil;

import static java.lang.String.format;
import static java.util.Arrays.asList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;

public final class Assertions {

  private Assertions() {
  }

  public static void assertStringEqual(String actual, String... expected) {
    List<String> actuals = asList(actual.split("\n"));
    int actualSize = actuals.size();
    assertThat(asList(expected), hasSize(actualSize));

    for (int i=0; i<actualSize; i++) {
      assertThat(format("Mismatch on line %d", i), expected[i], is(actuals.get(i)));
    }
  }
}

