package no.havard.javaflow.phases.verifier;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import static no.havard.javaflow.definitions.Objects.isObject;
import static no.havard.javaflow.definitions.Primitives.isPrimitive;
import static no.havard.javaflow.util.Lists.union;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.havard.javaflow.ast.Class;
import no.havard.javaflow.ast.Field;
import no.havard.javaflow.ast.Type;
import no.havard.javaflow.exceptions.MissingTypeException;
import no.havard.javaflow.model.CanonicalName;
import no.havard.javaflow.util.Lists;
import no.havard.javaflow.util.TypeMap;

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

