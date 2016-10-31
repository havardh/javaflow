package no.havard.javaflow.phases.transform;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import no.havard.javaflow.ast.Class;
import no.havard.javaflow.ast.Definition;
import no.havard.javaflow.ast.Parent;

public class InheritanceTransformer implements Transformer {

  public void transform(List<Definition> definitions) {

    List<Class> classes = definitions.stream()
        .filter(definition -> definition instanceof Class)
        .map(definition -> (Class) definition)
        .collect(toList());

    Map<String, Class> definitionMap = classes.stream()
        .collect(toMap(Class::getCanonicalName, identity()));

    classes.forEach(setParentReference(definitionMap));
  }

  private static Consumer<Class> setParentReference(Map<String, Class> definitionMap) {
    return definition -> definition.getParent().ifPresent(updateParentReference(definitionMap));
  }

  private static Consumer<Parent> updateParentReference(Map<String, Class> definitionMap) {
    return parent -> parent.setReference(definitionMap.get(parent.getCanonicalName()));
  }

}

