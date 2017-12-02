package com.github.havardh.javaflow.phases.transform;

import java.util.List;

import com.github.havardh.javaflow.ast.Type;

/**
 * Transformer to sort a {@code Type} list alphabetically based
 * on type names.
 */
public class SortedTypeTransformer implements Transformer {

  /**
   * Sorts the {@code Type} list alphabetically on name
   *
   * @param types the list to sort
   */
  @Override
  public void transform(List<Type> types) {
    types.sort((o1, o2) -> {
      String name1 = o1.getCanonicalName().toString();
      String name2 = o2.getCanonicalName().toString();

      return name1.compareToIgnoreCase(name2);
    });
  }
}

