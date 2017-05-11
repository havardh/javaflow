package com.github.havardh.javaflow.phases.filetransform;

/**
 * A {@code FileTranformer} to prepend a file with a comment
 */
public class CommentPrependTransformer implements FileTransformer {

  private final String comment;

  /**
   * Creates a {@code CommentPrependTransformer} with a
   * comment to prepend files with.
   * @param comment
   */
  public CommentPrependTransformer(String comment) {
    this.comment = comment;
  }

  /**
   * Prepend the file with the given comment
   *
   * @param file the file contents to transform
   * @return the file with the comment prepended to it
   */
  @Override
  public String transform(String file) {
    return "/* " + comment + " */\n" + file;
  }
}

