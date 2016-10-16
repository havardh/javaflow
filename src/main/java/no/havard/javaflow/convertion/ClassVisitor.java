package no.havard.javaflow.convertion;

import static no.havard.javaflow.model.DefinitionBuilder.DefinitionType.Class;

import no.havard.javaflow.model.DefinitionBuilder;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassVisitor extends VoidVisitorAdapter<DefinitionBuilder> {

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



