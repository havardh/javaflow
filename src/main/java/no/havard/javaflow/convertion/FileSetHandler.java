package no.havard.javaflow.convertion;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import static no.havard.javaflow.model.ClassDefinitionBuilder.classDefinitionBuilder;

import java.util.List;
import java.util.Map;

import no.havard.javaflow.model.ClassDefinition;
import no.havard.javaflow.model.Definition;
import no.havard.javaflow.model.FieldDefinition;

public class FileSetHandler {

  public static List<Definition> handleExtends(List<Definition> definitions) {
    Map<String, Definition> definitionMap = definitions.stream()
        .collect(toMap(Definition::getName, identity()));

    return definitions.stream()
        .map(definition ->
            definition.getParent()
                .map(name -> classDefinitionBuilder()
                    .withFields(((ClassDefinition)definitionMap
                        .getOrDefault(name, ClassDefinition.empty()))
                        .getFieldDefinitions().toArray(new FieldDefinition[]{}))
                    .with((ClassDefinition) definition)
                    .build())
                .orElse((ClassDefinition)definition)
        ).collect(toList());
  }

}

