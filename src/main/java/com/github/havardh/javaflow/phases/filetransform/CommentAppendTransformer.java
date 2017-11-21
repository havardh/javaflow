package com.github.havardh.javaflow.phases.filetransform;

public class CommentAppendTransformer implements FileTransformer {

  private final String comment;

  public CommentAppendTransformer(String comment) {
    this.comment = comment;
  }

  @Override
  public String transform(String file) {
    return "/* " + comment + " */\n" + file;
  }
}

