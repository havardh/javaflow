package no.havard.javaflow.convertion;

import static no.havard.javaflow.model.DefinitionBuilder.DefinitionType.Enum;

import no.havard.javaflow.model.DefinitionBuilder;

import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class EnumVisitor extends VoidVisitorAdapter<DefinitionBuilder> {

  @Override
  public void visit(EnumDeclaration n, DefinitionBuilder builder) {
    super.visit(n, builder);
    builder.withType(Enum);
    builder.withName(n.getName());

    n.getEntries().stream().forEach(dec -> builder.withEnumValue(dec.getName()));
  }

}

