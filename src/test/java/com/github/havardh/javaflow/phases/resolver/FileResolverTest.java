package com.github.havardh.javaflow.phases.resolver;

import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class FileResolverTest {

    private static final String BASE_PATH = "src/test/java/com/github/havardh/javaflow/model/";
    private static final String ABSOLUTE_BASE_PATH = Paths.get("./", BASE_PATH).normalize().toFile().getAbsolutePath() + "/";
    private final FileResolver resolver = new FileResolver();

    @Test
    public void shouldResolveFileByRelativePath() {
        List<Path> resolvedPaths = resolver.resolve(BASE_PATH + "Model.java");

        assertThat(resolvedPaths, hasSize(1));
    }

    @Test
    public void shouldResolveFileByAbsolutePath() {
        List<Path> resolvedPaths = resolver.resolve(ABSOLUTE_BASE_PATH + "Model.java");

        assertThat(resolvedPaths, hasSize(1));
    }

    @Test
    public void shouldResolveFilesInDirectoryByRelativePath() {
        List<Path> resolvedPaths = resolver.resolve(BASE_PATH);

        assertThat(resolvedPaths, hasSize(greaterThan(0)));
    }

    @Test
    public void shouldResolveFilesInDirectoryByAbsolutePath() {
        List<Path> resolvedPaths = resolver.resolve(ABSOLUTE_BASE_PATH);

        assertThat(resolvedPaths, hasSize(greaterThan(0)));
    }

    @Test
    public void shouldResolveFilesInChildDirectories() {
        List<Path> resolvedPaths = resolver.resolve(BASE_PATH);

        Path expectedPath = Paths.get(BASE_PATH, "packaged/PackagedMember.java");
        assertThat(resolvedPaths, hasItem(equalTo(expectedPath)));
    }

    @Test
    public void shouldResolveFileByRelativeGlobPattern() {
        List<Path> paths = resolver.resolve(BASE_PATH + "**/*.java");

        assertThat(paths, hasSize(greaterThan(0)));
    }

    @Test
    public void shouldResolveFileByAbsoluteGlobPattern() {
        List<Path> paths = resolver.resolve(ABSOLUTE_BASE_PATH + "**/*.java");

        assertThat(paths, hasSize(greaterThan(0)));
    }

    @Test
    public void shouldResolveFileByPathSegment() {
        List<Path> paths = resolver.resolve("**/packaged/*.java");

        Path expectedPath = Paths.get(BASE_PATH, "packaged/PackagedMember.java");
        assertThat(paths, contains(equalTo(expectedPath)));
    }

    @Test
    public void shouldResolveRelativeGlobPatternLocally() {
        List<Path> paths = resolver.resolve("*.java");

        assertThat(paths, hasSize(0));
    }
}
