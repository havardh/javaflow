package com.github.havardh.javaflow.phases.transform;

import java.util.List;

import com.github.havardh.javaflow.ast.Type;

public interface Transformer {
  void transform(List<Type> types);
}
