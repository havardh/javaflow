package com.github.havardh.javaflow.exceptions;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.stream.IntStream;

public class AggregatedException extends RuntimeException {

  private final boolean showEnumerationInErrors;
  private final List<Exception> exceptions;

  public AggregatedException(String message, List<Exception> exceptions, boolean showEnumerationInErrors) {
    super(message);
    this.exceptions = exceptions;
    this.showEnumerationInErrors = showEnumerationInErrors;
  }

  public List<Exception> getExceptions() {
    return exceptions;
  }

  public String getMessage() {
    return super.getMessage() + "\n" + this.getSubMessages();
  }

  private String getSubMessages() {
    return IntStream
        .range(0, exceptions.size())
        .mapToObj(i -> getSubmessagePrefix(i) + exceptions.get(i).getMessage())
        .collect(joining("\n\n"));
  }

  private String getSubmessagePrefix(int errorNumber) {
    return showEnumerationInErrors ? format("%d) ", errorNumber + 1) : "";
  }
}
