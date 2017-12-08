package com.github.havardh.javaflow.exceptions;

import java.util.List;
import java.util.stream.Collectors;

public class AggregatedException extends RuntimeException {

  private final List<Exception> exceptions;

  public AggregatedException(String message, List<Exception> exceptions) {
    super(message + exceptions.stream().map(Exception::getMessage).collect(Collectors.joining("\n")));
    this.exceptions = exceptions;
  }

  public List<Exception> getExceptions() {
    return exceptions;
  }
}
