package com.github.havardh.javaflow.phases.parser.java;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.github.havardh.javaflow.ast.Field;
import com.github.havardh.javaflow.ast.Method;
import com.github.havardh.javaflow.ast.Parent;
import com.github.havardh.javaflow.ast.builders.ClassBuilder;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * A JavaFlow visitor for the {@code enum} java declaration.
 */
public class ClassVisitor extends VoidVisitorAdapter<ClassBuilder> {

  private final boolean includeStaticFields;

  private String packageName;
  private Map<String, String> imports = new HashMap<>();
  private Deque<String> classNames = new ArrayDeque<>();

  public ClassVisitor(boolean includeStaticFields) {
    this.includeStaticFields = includeStaticFields;
  }

  /** {@inheritDoc} */
  @Override
  public void visit(PackageDeclaration n, ClassBuilder builder) {
    super.visit(n, builder);

    packageName = n.getNameAsString();
    builder.withPackageName(n.getNameAsString());
  }

  /** {@inheritDoc} */
  @Override
  public void visit(ImportDeclaration n, ClassBuilder builder) {
    super.visit(n, builder);

    String typeName = n.getName().getIdentifier();
    String packageName = n.getName().getQualifier().map(Name::asString).orElse("");

    imports.put(typeName, packageName);
  }

  /** {@inheritDoc} */
  @Override
  public void visit(ClassOrInterfaceDeclaration n, ClassBuilder builder) {
    classNames.addLast(n.getNameAsString());

    if (n.getParentNode().isPresent() && n.getParentNode().get() instanceof CompilationUnit) {
      super.visit(n, builder);
      setAttributes(builder, n);
    } else {
      String fullPackageName = packageName + "."
          + classNames.stream().limit(classNames.size() - 1).collect(joining("."));

      imports.put(n.getNameAsString(), fullPackageName);

      ClassBuilder child = ClassBuilder.classBuilder();
      child.withPackageName(fullPackageName);
      setAttributes(child, n);
      super.visit(n, child);
      builder.withInnerClass(child.build());
    }

    classNames.removeLast();
  }

  private void setAttributes(ClassBuilder builder, ClassOrInterfaceDeclaration n) {
    builder.withName(n.getNameAsString());
    if (isClass(n)) {
      CanonicalNameFactory factory = new CanonicalNameFactory(packageName, imports);
      n.getExtendedTypes().stream().findFirst()
          .ifPresent(parent -> builder.withParent(new Parent(factory.build(parent.getNameAsString()))));
    }
  }

  /** {@inheritDoc} */
  @Override
  public void visit(FieldDeclaration field, ClassBuilder builder) {
    super.visit(field, builder);
    TypeFactory factory = new TypeFactory(packageName, imports);

    if (shouldIncludeStaticFields(field.getModifiers())) {
      field.getVariables().forEach(variable -> builder.withField(new Field(
          isNullable(field),
          variable.getNameAsString(),
          factory.build(variable.getType().asString(), variable.getType() instanceof PrimitiveType)
      )));
    }
  }

  @Override
  public void visit(MethodDeclaration method, ClassBuilder builder) {
    super.visit(method, builder);
    TypeFactory factory = new TypeFactory(packageName, imports);

    if (method.getParentNode().isPresent()
        && method.getParentNode().get() instanceof ClassOrInterfaceDeclaration
        && isClass((ClassOrInterfaceDeclaration) method.getParentNode().get())
        && isGetter(method.getNameAsString(), method.getType().asString())
        && shouldIncludeStaticFields(method.getModifiers())) {
      builder.withGetter(new Method(
          method.getNameAsString(),
          factory.build(method.getType().asString(), method.getType() instanceof PrimitiveType)
      ));
    }
  }

  private boolean shouldIncludeStaticFields(Set<Modifier> modifiers) {
    return includeStaticFields || !modifiers.contains(Modifier.STATIC);
  }

  private boolean isNullable(FieldDeclaration field) {
    return field.getAnnotations().stream()
        .map(AnnotationExpr::getNameAsString)
        .anyMatch(name -> name.equals("Nullable"));
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
