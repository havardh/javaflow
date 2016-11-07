package no.havard.javaflow.phases.parser.java;

import static java.util.stream.Collectors.joining;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import no.havard.javaflow.ast.Field;
import no.havard.javaflow.ast.Parent;
import no.havard.javaflow.ast.builders.ClassBuilder;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassVisitor extends VoidVisitorAdapter<ClassBuilder> {

  private String packageName;
  private Map<String, String> imports = new HashMap<>();

  @Override
  public void visit(PackageDeclaration n, ClassBuilder builder) {
    super.visit(n, builder);

    packageName = n.getPackageName();
    builder.withPackageName(n.getPackageName());
  }

  @Override
  public void visit(ImportDeclaration n, ClassBuilder builder) {
    super.visit(n, builder);

    String[] packages = n.getName().toString().split("\\.");
    String typeName = n.getName().getName();
    String packageName = Stream.of(packages).limit(packages.length-1).collect(joining("."));

    imports.put(typeName, packageName);
  }

  @Override
  public void visit(ClassOrInterfaceDeclaration n, ClassBuilder builder) {
    super.visit(n, builder);
    builder.withName(n.getName());
    CanonicalNameFactory factory = new CanonicalNameFactory(packageName, imports);

    n.getExtends().stream().findFirst()
        .ifPresent(parent -> builder.withParent(new Parent(factory.build(parent.getName()))));
  }

  @Override
  public void visit(FieldDeclaration field, ClassBuilder builder) {
    super.visit(field, builder);
    TypeFactory factory = new TypeFactory(packageName, imports);

    field.getVariables().forEach(variable -> builder.withField(new Field(
        isNullable(field),
        variable.getId().getName(),
        factory.build(field.getType())
    )));
  }

  private boolean isNullable(FieldDeclaration field) {
    return field.getAnnotations().stream()
        .map(AnnotationExpr::getName)
        .map(NameExpr::getName)
        .filter(name -> name.equals("Nullable"))
        .count() > 0;
  }

}
