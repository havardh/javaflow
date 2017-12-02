package com.github.havardh.javaflow.phases.verifier;

import java.util.List;

import com.github.havardh.javaflow.ast.Type;

/**
 * Interface for verifiers on list of {@code Type}.
 *
 * The verifiers reads all the internal {@code Type} classes and runs
 * static verification to ensure that the types are internally consistent
 * to provide more detailed errors if they are not.
 */
public interface Verifier {

  /**
   * Verifies that the list of {@code Type} are internally
   * consistent. If they are not the {@code Verifier} should
   * throw a {@code RuntimeException} to inform the user what
   * part of the input is non consistent.
   *
   * @param types list of {@code Type} to verify
   */
  void verify(List<Type> types);

}

