package com.github.havardh.javaflow.phases.resolver;

import com.github.havardh.javaflow.exceptions.ExitException;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Resolver to resolve a single pattern into a list of matching files
 */
public class FileResolver {

  /**
   * Reads a single pattern into a list of matching files
   * The patterns can either be a relative or absolute path
   * to a file or directory. Or a relative or absolute glob.
   * All patterns are relative to the working directory of
   * the process.
   *
   * @param pattern the file, directory or glob pattern
   * @return a list of paths matching the pattern
   */
  public List<Path> resolve(String pattern) {
    try {
      Path path = Paths.get(".").resolve(pattern);
      if (Files.isRegularFile(path)) {
        return normalize(Collections.singletonList(path));
      } else if (Files.isDirectory(path)) {
        return normalize(ofDirectory(path));
      } else {
        return normalize(ofPattern(pattern));
      }
    } catch (IOException e) {
      throw new ExitException(ExitException.ErrorCode.COULD_NOT_RESOLVE_PATTERN, e);
    }
  }

  private static List<Path> ofDirectory(Path path) throws IOException {
    FileVisitor visitor = FileVisitor.matchAllFiles();
    Files.walkFileTree(Paths.get(".").resolve(path), visitor);
    return visitor.getPaths();
  }

  private static List<Path> ofPattern(String pattern) throws IOException {
    FileVisitor visitor = FileVisitor.matchingGlob(pattern);
    Files.walkFileTree(Paths.get("."), visitor);
    return visitor.getPaths();
  }

  private static List<Path> normalize(List<Path> paths) {
    return paths.stream().map(Path::normalize).collect(Collectors.toList());
  }

  private static final class FileVisitor extends SimpleFileVisitor<Path> {

    private final PathMatcher pathMatcher;
    private final List<Path> paths = new ArrayList<>();

    private FileVisitor() {
      this.pathMatcher = (p) -> true;
    }

    private FileVisitor(String pattern) {
      this.pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
    }

    static FileVisitor matchAllFiles() {
      return new FileVisitor();
    }

    static FileVisitor matchingGlob(String glob) {
      return new FileVisitor(glob);
    }

    List<Path> getPaths() {
      return paths;
    }


    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
      if (attrs.isRegularFile()) {
        Path normalized = file.normalize();
        if (pathMatcher.matches(normalized) || pathMatcher.matches(normalized.toAbsolutePath())) {
          paths.add(file);
        }
      }

      return FileVisitResult.CONTINUE;
    }
  }

}
