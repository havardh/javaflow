package com.github.havardh.javaflow.phases.verifier;

import static java.lang.String.format;
import static java.util.Collections.singletonList;

import java.util.ArrayList;
import java.util.List;

import com.github.havardh.javaflow.ast.Class;
import com.github.havardh.javaflow.ast.Field;
import com.github.havardh.javaflow.ast.Method;
import com.github.havardh.javaflow.ast.Type;
import com.github.havardh.javaflow.exceptions.AggregatedException;
import com.github.havardh.javaflow.exceptions.FieldGettersMismatchException;

public class ClassGetterNamingVerifier implements Verifier {

  /**
   * Verifies that the given types all have:
   * - the same number of fields and getters,
   * - the same type in the field definitions as the return type in the corresponding getter and
   * - their getters for the corresponding fields with the same name, prefixed with `get` or `is`.
   *
   * @param types list of {@code Type} to verify.
   */
  @Override
  public void verify(List<Type> types) {
    List<Exception> exceptions = new ArrayList<>();
    for (Type type : types) {
      if (type instanceof Class) {
        exceptions.addAll(validate((Class) type));
      }
    }

    if (!exceptions.isEmpty()) {
      throw new AggregatedException("Class getter naming validation failed with following errors:\n", exceptions, true);
    }
  }

  private List<Exception> validate(Class classToValidate) {
    List<Method> getters = classToValidate.getGetters();
    List<Field> fields = classToValidate.getFields();
    if (getters.size() != fields.size()) {
      return singletonList(new FieldGettersMismatchException(classToValidate.getCanonicalName(), format(
          "Number of getters and fields is not the same.\n" +
              "Fields in model: %s\n" +
              "Getters in model: %s",
          fields,
          getters
      )));
    }
    List<Exception> exceptions = new ArrayList<>();
    for (Method getter : getters) {
      try {
        Field correspondingField = findFieldByGetter(classToValidate, fields, getter);

        if (!correspondingField.getType().equals(getter.getType())) {
          throw new FieldGettersMismatchException(
              classToValidate.getCanonicalName(),
              format(
                  "Type of getter %s (%s) does not correspond to field %s (%s)",
                  getter.getName(),
                  getter.getType(),
                  correspondingField.getName(),
                  correspondingField.getType()
              )
          );
        }
      } catch (FieldGettersMismatchException e) {
        exceptions.add(e);
      }
    }
    return exceptions;
  }

  private Field findFieldByGetter(Class classToValidate, List<Field> fields, Method getter)
      throws FieldGettersMismatchException {
    return fields.stream()
        .filter(field -> field.getName().equals(convertGetterNameToFieldName(getter.getName())))
        .findFirst()
        .orElseThrow(() -> new FieldGettersMismatchException(
            classToValidate.getCanonicalName(),
            format("Name of getter %s does not correspond to any field name.", getter.getName())
        ));
  }

  private static String convertGetterNameToFieldName(String getterName) {
    if (getterName.startsWith("get") && getterName.length() > 3) {
      return Character.toLowerCase(getterName.charAt(3)) + (getterName.length() > 4 ? getterName.substring(4) : "");
    }

    if (getterName.startsWith("is") && getterName.length() > 2) {
      return Character.toLowerCase(getterName.charAt(2)) + (getterName.length() > 4 ? getterName.substring(3) : "");
    }

    return getterName;
  }
}
