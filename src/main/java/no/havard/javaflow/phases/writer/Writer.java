package no.havard.javaflow.phases.writer;

import java.io.IOException;

public interface Writer <T> {
  void write(T t, java.io.Writer writer) throws IOException;
}
