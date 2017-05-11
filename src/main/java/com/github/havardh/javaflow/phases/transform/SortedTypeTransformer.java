package com.github.havardh.javaflow.phases.transform;

import java.util.List;

import com.github.havardh.javaflow.ast.Type;

public class SortedTypeTransformer implements Transformer {

  @Override
  public void transform(List<Type> types) {
    types.sort((o1, o2) -> {
      String name1 = o1.getCanonicalName().toString();
      String name2 = o2.getCanonicalName().toString();

      return name1.compareToIgnoreCase(name2);
    });
  }
}

