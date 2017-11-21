package com.github.havardh.javaflow.phases.verifier;

import java.util.List;

import com.github.havardh.javaflow.ast.Type;

public interface Verifier {

  void verify(List<Type> types);

}

