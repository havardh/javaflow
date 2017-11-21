package com.github.havardh.javaflow.phases.transform;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.github.havardh.javaflow.ast.Class;
import com.github.havardh.javaflow.ast.Parent;
import com.github.havardh.javaflow.ast.Type;

public class InheritanceTransformer implements Transformer {

  public void transform(List<Type> types) {

    List<Class> classes = types.stream()
        .filter(type -> type instanceof Class)
        .map(type -> (Class) type)
        .collect(toList());

    Map<String, Class> typeMap = classes.stream()
        .collect(toMap(Class::getFullName, identity()));

    classes.forEach(setParentReference(typeMap));
  }

  private static Consumer<Class> setParentReference(Map<String, Class> typeMap) {
    return aClass -> aClass.getParent().ifPresent(updateParentReference(typeMap));
  }

  private static Consumer<Parent> updateParentReference(Map<String, Class> typeMap) {
    return parent -> parent.setReference(typeMap.get(parent.getCanonicalName()));
  }

}

