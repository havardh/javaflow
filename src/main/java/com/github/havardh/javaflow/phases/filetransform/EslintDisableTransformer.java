package com.github.havardh.javaflow.phases.filetransform;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;

public class EslintDisableTransformer implements FileTransformer {

  private List<String> rules = new ArrayList<>();

  public EslintDisableTransformer(List<String> rules) {
    this.rules = rules;
  }

  @Override
  public String transform(String file) {
    String allRules = rules.stream()
        .map(rule -> "/* eslint-disable " + rule + " */")
        .collect(joining("\n"));

    return allRules + "\n" + file;
  }
}

