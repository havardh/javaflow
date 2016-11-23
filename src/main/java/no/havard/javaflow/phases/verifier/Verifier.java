package no.havard.javaflow.phases.verifier;

import java.util.List;

import no.havard.javaflow.ast.Type;

public interface Verifier {

  void verify(List<Type> types);

}

