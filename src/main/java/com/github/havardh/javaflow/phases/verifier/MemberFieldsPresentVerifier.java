package com.github.havardh.javaflow.phases.verifier;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import static com.github.havardh.javaflow.definitions.Objects.isObject;
import static com.github.havardh.javaflow.definitions.Primitives.isPrimitive;
import static com.github.havardh.javaflow.util.Lists.union;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.havardh.javaflow.ast.Class;
import com.github.havardh.javaflow.ast.Field;
import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.exceptions.MissingTypeException;
import com.github.havardh.javaflow.model.CanonicalName;
import com.github.havardh.javaflow.util.TypeMap;

public class MemberFieldsPresentVerifier implements Verifier {

  private final TypeMap customTypes;

  public MemberFieldsPresentVerifier(TypeMap customTypes) {
    this.customTypes = customTypes;
  }

  @Override
  public void verify(List<Type> types) {
    Set<CanonicalName> nameSet = types.stream()
        .map(Type::getCanonicalName)
        .collect(toSet());

    Map<Type, List<Field>> missingTypes = new HashMap<>();
    for (Type type : types.stream().filter(t -> t instanceof Class).collect(toList())) {
      ((Class) type).getFields().stream()
          .filter(field -> !nameSet.contains(field.getType().getCanonicalName()))
          .filter(field -> !isObject(field.getType().getFullName()))
          .filter(field -> !isPrimitive(field.getType().getFullName()))
          .filter(field -> !customTypes.containsKey(field.getType().getFullName()))
          .forEach(field -> missingTypes.compute(type, (ignored, fields) -> fields == null
              ? singletonList(field)
              : union(fields, singletonList(field))));
    }


    if (!missingTypes.isEmpty()) {
      throw new MissingTypeException(missingTypes);
    }
  }
}

