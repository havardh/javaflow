package no.havard.javaflow.phases.reader.java;

import no.havard.javaflow.ast.builders.EnumBuilder;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class EnumVisitor extends VoidVisitorAdapter<EnumBuilder> {

  @Override
  public void visit(PackageDeclaration n, EnumBuilder builder) {
    super.visit(n, builder);
    builder.withPackageName(n.getPackageName());
  }

  @Override
  public void visit(EnumDeclaration n, EnumBuilder builder) {
    super.visit(n, builder);
    builder.withName(n.getName());
  }

  @Override
  public void visit(EnumConstantDeclaration n, EnumBuilder builder) {
    super.visit(n, builder);
    builder.withEnumValue(n.getName());
  }

}

