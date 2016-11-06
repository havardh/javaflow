package no.havard.javaflow.phases.transform;

import java.util.List;

import no.havard.javaflow.ast.Type;

public interface Transformer {
  void transform(List<Type> types);
}
