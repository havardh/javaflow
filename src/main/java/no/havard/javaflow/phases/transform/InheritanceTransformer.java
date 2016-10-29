package no.havard.javaflow.phases.transform;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import no.havard.javaflow.model.Definition;
import no.havard.javaflow.model.Parent;

public class InheritanceTransformer implements Transformer {

  public void transform(List<Definition> definitions) {
    Map<String, Definition> definitionMap = definitions.stream()
        .collect(toMap(Definition::getCanonicalName, identity()));

    definitions.forEach(setParentReference(definitionMap));
  }

  private static Consumer<Definition> setParentReference(Map<String, Definition> definitionMap) {
    return definition -> definition.getParent().ifPresent(updateParentReference(definitionMap));
  }

  private static Consumer<Parent> updateParentReference(Map<String, Definition> definitionMap) {
    return parent -> parent.setReference(definitionMap.get(parent.getCanonicalName()));
  }

}

