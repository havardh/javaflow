package com.github.havardh.javaflow.phases.filetransform;

public class CommentPrependTransformer implements FileTransformer {

  private final String comment;

  public CommentPrependTransformer(String comment) {
    this.comment = comment;
  }

  @Override
  public String transform(String file) {
    return "/* " + comment + " */\n" + file;
  }
}

