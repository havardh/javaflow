package com.github.havardh.javaflow.exceptions;

import static java.util.Arrays.asList;

import static com.github.havardh.javaflow.testutil.Assertions.assertStringEqual;

import org.junit.jupiter.api.Test;

public class AggregatedExceptionTest {
  @Test
  public void shouldPrintMessageWithSubMessages() {
    AggregatedException exception = new AggregatedException(
        "Aggregated title",
        asList(new Exception("First exception"), new Exception("Second exception")),
        false
    );

    assertStringEqual(
        exception.getMessage(),
        "Aggregated title",
        "First exception",
        "",
        "Second exception"
    );
  }

  @Test
  public void shouldPrintMessageWithEnumeratedSubMessages() {
    AggregatedException exception = new AggregatedException(
        "Aggregated title",
        asList(new Exception("First exception"), new Exception("Second exception")),
        true
    );

    assertStringEqual(
        exception.getMessage(),
        "Aggregated title",
        "1) First exception",
        "",
        "2) Second exception"
    );
  }
}
