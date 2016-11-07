package no.havard.javaflow.phases.parser;

import java.util.Optional;

import no.havard.javaflow.ast.Type;

public interface Parser {
  Optional<Type> parse(String filename);
}

