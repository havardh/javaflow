package no.havard.javaflow.convertion;

import static no.havard.javaflow.model.DefinitionBuilder.DefinitionType.Class;
import static no.havard.javaflow.model.DefinitionBuilder.DefinitionType.Enum;

import no.havard.javaflow.model.DefinitionBuilder;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MemberVisitor extends VoidVisitorAdapter<DefinitionBuilder> {

  @Override
  public void visit(EnumDeclaration n, DefinitionBuilder builder) {
    super.visit(n, builder);
    builder.withType(Enum);
    builder.withName(n.getName());

    n.getEntries().stream().forEach(dec -> builder.withEnumValue(dec.getName()));
  }

  @Override
  public void visit(ClassOrInterfaceDeclaration n, DefinitionBuilder builder) {
    super.visit(n, builder);
    builder.withName(n.getName());
    builder.withType(Class);
  }

  @Override
  public void visit(FieldDeclaration field, DefinitionBuilder builder) {
    super.visit(field, builder);

    field.getVariables().stream()
        .forEach(variable -> builder.withField(field.getType(), variable.getId().getName()));
  }
}

