package com.github.havardh.javaflow.phases.transform;

import java.util.List;

import com.github.havardh.javaflow.ast.Type;

/**
 * Interface to transformers of {@code Type} lists.
 */
public interface Transformer {

  /**
   * Transforms the {@code Type} list in-place.
   *
   * @param types the list to transform
   */
  void transform(List<Type> types);
}
