package no.havard.javaflow.phases.reader;

import java.util.Optional;

import no.havard.javaflow.ast.Type;

public interface Reader {
  Optional<Type> read(String filename);
}

