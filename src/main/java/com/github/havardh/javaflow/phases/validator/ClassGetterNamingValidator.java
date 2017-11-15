package com.github.havardh.javaflow.phases.validator;

import static java.lang.String.format;

import java.util.List;

import com.github.havardh.javaflow.ast.Class;
import com.github.havardh.javaflow.ast.Field;
import com.github.havardh.javaflow.ast.Method;
import com.github.havardh.javaflow.exceptions.FieldGettersMismatchException;

public class ClassGetterNamingValidator {

  public static void validate(Class classToValidate) {
    List<Method> getters = classToValidate.getGetters();
    List<Field> fields = classToValidate.getFields();
    if (getters.size() != fields.size()) {
      throw new FieldGettersMismatchException(format(
          "Model %s is not a pure DTO. Number of getters and fields is not same.",
          classToValidate.getFullName()
      ));
    }
    for (Method getter : getters) {
      fields.stream()
          .filter(field -> field.getName().equals(convertGetterNameToFieldName(getter.getName())))
          .findFirst()
          .orElseThrow(() -> new FieldGettersMismatchException(format(
              "Model %s is not a pure DTO. Name of getter %s does not correspond to any field name.",
              classToValidate.getFullName(), getter.getName()
          )));
    }
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
