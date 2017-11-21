package com.github.havardh.javaflow.phases.parser;

import java.util.Optional;

import com.github.havardh.javaflow.ast.Type;

public interface Parser {
  Optional<Type> parse(String filename);
}

