package com.github.havardh.javaflow.util;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

/**
 * Static utilities for {@code List}
 */
public final class Lists {
  private Lists() {
  }

  /**
   * Concatenates two lists of type {@code T}
   *
   * @param a the first list
   * @param b the second list
   * @param <T> the type of the lists
   * @return the concatenation of {@code a} and {@code b}
   */
  public static <T> List<T> concat(List<T> a, List<T> b) {
    return Stream.concat(a.stream(), b.stream()).collect(toList());
  }
}

