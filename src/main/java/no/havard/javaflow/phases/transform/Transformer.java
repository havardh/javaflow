package no.havard.javaflow.phases.transform;

import java.util.List;

import no.havard.javaflow.ast.Definition;

public interface Transformer {
  void transform(List<Definition> definitions);
}
