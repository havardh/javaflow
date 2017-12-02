package com.github.havardh.javaflow.phases.verifier;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import static com.github.havardh.javaflow.phases.writer.flow.converter.definitions.Objects.isObject;
import static com.github.havardh.javaflow.phases.writer.flow.converter.definitions.Primitives.isPrimitive;
import static com.github.havardh.javaflow.util.Lists.concat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.havardh.javaflow.ast.Class;
import com.github.havardh.javaflow.ast.Field;
import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.exceptions.MissingTypeException;
import com.github.havardh.javaflow.model.CanonicalName;
import com.github.havardh.javaflow.model.TypeMap;

public class MemberFieldsPresentVerifier implements Verifier {

  private final TypeMap customTypes;

  /**
   * Create a {@code MemberFieldPresentVerifier} for the given
   * {@code TypeMap}.
   *
   * @param customTypes the {@code TypeMap} to verify based on
   */
  public MemberFieldsPresentVerifier(TypeMap customTypes) {
    this.customTypes = customTypes;
  }

  /**
   * Verifies that there are no missing types referenced
   * in the list of {@code Type}.
   *
   * A missing type is found when a type is referenced which is not
   * in the given set of types of in the {@code TypeMap} of the verifier
   * If a any missing types are discovered a {@code MissingTypeException}
   * is thrown containing the complete list of missing types.
   *
   * @param types list of {@code Type} to verify
   * @throws MissingTypeException when a missing type is found
   */
  @Override
  public void verify(List<Type> types) {
    Set<CanonicalName> nameSet = types.stream()
        .map(Type::getCanonicalName)
        .collect(toSet());

    Map<Type, List<Field>> missingTypes = new HashMap<>();
    for (Type type : types.stream().filter(t -> t instanceof Class).collect(toList())) {
      ((Class) type).getFields().stream()
          .filter(field -> !nameSet.contains(field.getType().getCanonicalName()))
          .filter(field -> !isObject(field.getType().toString()))
          .filter(field -> !isPrimitive(field.getType().toString()))
          .filter(field -> !customTypes.containsKey(field.getType().toString()))
          .forEach(field -> missingTypes.compute(type, (ignored, fields) -> fields == null
              ? singletonList(field)
              : concat(fields, singletonList(field))));
    }


    if (!missingTypes.isEmpty()) {
      throw new MissingTypeException(missingTypes);
    }
  }
}

