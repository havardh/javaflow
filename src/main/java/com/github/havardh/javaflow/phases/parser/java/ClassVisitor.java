package com.github.havardh.javaflow.phases.parser.java;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.github.havardh.javaflow.ast.Field;
import com.github.havardh.javaflow.ast.Method;
import com.github.havardh.javaflow.ast.Parent;
import com.github.havardh.javaflow.ast.builders.ClassBuilder;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * A JavaFlow visitor for the {@code enum} java declaration.
 */
public class ClassVisitor extends VoidVisitorAdapter<ClassBuilder> {

  private String packageName;
  private Map<String, String> imports = new HashMap<>();

  /** {@inheritDoc} */
  @Override
  public void visit(PackageDeclaration n, ClassBuilder builder) {
    super.visit(n, builder);

    packageName = n.getPackageName();
    builder.withPackageName(n.getPackageName());
  }

  /** {@inheritDoc} */
  @Override
  public void visit(ImportDeclaration n, ClassBuilder builder) {
    super.visit(n, builder);

    String[] packages = n.getName().toString().split("\\.");
    String typeName = n.getName().getName();
    String packageName = Stream.of(packages).limit(packages.length - 1).collect(joining("."));

    imports.put(typeName, packageName);
  }

  /** {@inheritDoc} */
  @Override
  public void visit(ClassOrInterfaceDeclaration n, ClassBuilder builder) {
    super.visit(n, builder);
    builder.withName(n.getName());
    CanonicalNameFactory factory = new CanonicalNameFactory(packageName, imports);
    if (isClass(n)) {
      n.getExtends().stream().findFirst()
          .ifPresent(parent -> builder.withParent(new Parent(factory.build(parent.getName()))));
    }
  }

  /** {@inheritDoc} */
  @Override
  public void visit(FieldDeclaration field, ClassBuilder builder) {
    super.visit(field, builder);
    TypeFactory factory = new TypeFactory(packageName, imports);

    field.getVariables().forEach(variable -> builder.withField(new Field(
        isNullable(field),
        variable.getId().getName(),
        factory.build(field.getType().toString(), field.getType() instanceof PrimitiveType)
    )));
  }

  @Override
  public void visit(MethodDeclaration method, ClassBuilder builder) {
    super.visit(method, builder);
    TypeFactory factory = new TypeFactory(packageName, imports);

    if (isClass((ClassOrInterfaceDeclaration) method.getParentNode()) && isGetter(method.getName(), method.getType().toString())) {
      builder.withGetter(new Method(
          method.getName(),
          factory.build(method.getType().toString(), method.getType() instanceof PrimitiveType)
      ));
    }
  }

  private boolean isNullable(FieldDeclaration field) {
    return field.getAnnotations().stream()
        .map(AnnotationExpr::getName)
        .map(NameExpr::getName)
        .filter(name -> name.equals("Nullable"))
        .count() > 0;
  }

  private boolean isClass(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
    return !classOrInterfaceDeclaration.isInterface();
  }

  private boolean isGetter(String name, String type) {
    if (asList("boolean", "Boolean").contains(type) && name.startsWith("is")) {
      return true;
    }

    return name.startsWith("get");
  }

}
