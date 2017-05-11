package com.github.havardh.javaflow.phases.filetransform;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@code FileTransformer} to append eslint-disable rules to
 * the beginning of an output file.
 */
public class EslintDisableTransformer implements FileTransformer {

  private List<String> rules = new ArrayList<>();

  /**
   * Create a {@code EslintDisableTranformer} with a list of rules
   * to disable.
   *
   * @param rules the list of rules to disable
   */
  public EslintDisableTransformer(List<String> rules) {
    this.rules = rules;
  }

  /**
   * Prepend eslint-disable to the given file
   *
   * @param file the file contents to transform
   * @return the file prepended with the disable rules
   */
  @Override
  public String transform(String file) {
    String allRules = rules.stream()
        .map(rule -> "/* eslint-disable " + rule + " */")
        .collect(joining("\n"));

    return allRules + "\n" + file;
  }
}

