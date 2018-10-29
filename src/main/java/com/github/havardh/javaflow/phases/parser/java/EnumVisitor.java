package com.github.havardh.javaflow.phases.parser.java;

import com.github.havardh.javaflow.ast.builders.EnumBuilder;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * A JavaFlow visitor for the {@code enum} java declaration.
 */
public class EnumVisitor extends VoidVisitorAdapter<EnumBuilder> {

  /** {@inheritDoc} */
  @Override
  public void visit(PackageDeclaration n, EnumBuilder builder) {
    super.visit(n, builder);
    builder.withPackageName(n.getNameAsString());
  }

  /** {@inheritDoc} */
  @Override
  public void visit(EnumDeclaration n, EnumBuilder builder) {
    super.visit(n, builder);
    builder.withName(n.getNameAsString());
  }

  /** {@inheritDoc} */
  @Override
  public void visit(EnumConstantDeclaration n, EnumBuilder builder) {
    super.visit(n, builder);
    builder.withEnumValue(n.getNameAsString());
  }

}

