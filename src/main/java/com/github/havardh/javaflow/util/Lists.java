package com.github.havardh.javaflow.util;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

public final class Lists {
  private Lists() {
  }

  public static <T> List<T> concat(List<T> a, List<T> b) {
    return Stream.concat(a.stream(), b.stream()).collect(toList());
  }
}

