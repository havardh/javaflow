package no.havard.javaflow.convertion;

import no.havard.javaflow.model.builders.EnumDefinitionBuilder;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class EnumVisitor extends VoidVisitorAdapter<EnumDefinitionBuilder> {

  @Override
  public void visit(PackageDeclaration n, EnumDefinitionBuilder builder) {
    super.visit(n, builder);
    builder.withPackageName(n.getPackageName());
  }

  @Override
  public void visit(EnumDeclaration n, EnumDefinitionBuilder builder) {
    super.visit(n, builder);
    builder.withName(n.getName());
  }

  @Override
  public void visit(EnumConstantDeclaration n, EnumDefinitionBuilder builder) {
    super.visit(n, builder);
    builder.withEnumValue(n.getName());
  }

}

