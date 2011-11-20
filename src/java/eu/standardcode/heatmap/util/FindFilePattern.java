package eu.standardcode.heatmap.util;

import java.io.IOException;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Łukasz Sutkowski
 * @since 0.2.0
 */
public class FindFilePattern extends SimpleFileVisitor<Path> {

    private final PathMatcher matcher;
    private final List<Path> files = new LinkedList<>();
    private final Path begin;

    public FindFilePattern(Path begin, String pattern) {
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        this.begin = begin;
    }

    public List<Path> getFiles() throws IOException {
        Files.walkFileTree(begin, this);
        return files;
    }

    public List<Path> getFiles(int levels) throws IOException {
        Files.walkFileTree(begin, EnumSet.noneOf(FileVisitOption.class), levels, this);
        return files;
    }

    private void find(Path file) {
        Path name = file.getFileName();
        if (name != null && matcher.matches(name)) {
            files.add(begin.relativize(file));
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
        find(file);
        return CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        find(dir);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }
}
