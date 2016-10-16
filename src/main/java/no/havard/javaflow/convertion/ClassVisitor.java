package no.havard.javaflow.convertion;

import no.havard.javaflow.model.ClassDefinitionBuilder;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassVisitor extends VoidVisitorAdapter<ClassDefinitionBuilder> {

  @Override
  public void visit(ClassOrInterfaceDeclaration n, ClassDefinitionBuilder builder) {
    super.visit(n, builder);
    builder.withName(n.getName());
  }

  @Override
  public void visit(FieldDeclaration field, ClassDefinitionBuilder builder) {
    super.visit(field, builder);

    field.getVariables().stream()
      .forEach(variable -> builder.withField(field.getType(), variable.getId().getName()));
  }
}



