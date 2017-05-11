package com.github.havardh.javaflow.phases.parser;

import java.util.Optional;

import com.github.havardh.javaflow.ast.Type;

/**
 * Interface for a code @{code Parser}.
 *
 * The parser should parse source code into the internal JavaFlow
 * representation of a model
 */
public interface Parser {

  /**
   * Parse the given source code into a {@code Type}.
   *
   * @param sourceCode the source to parse
   * @return the parsed source as a {@code Type}
   */
  Optional<Type> parse(String sourceCode);
}

