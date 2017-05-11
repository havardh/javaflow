package com.github.havardh.javaflow.phases.writer;

import java.io.IOException;

/**
 * Interface for writing an object to a {@code java.io.Writer}.
 *
 * @param <T> the
 */
public interface Writer <T> {

  /**
   * Writes the given parameter to the {@code writer}.
   *
   * @param t the object to write
   * @param writer the writer to write to
   * @throws IOException thrown when the writer throws an IOException
   */
  void write(T t, java.io.Writer writer) throws IOException;
}
