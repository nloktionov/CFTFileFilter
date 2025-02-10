package ru.nloktionov.handler;

import ru.nloktionov.statistics.Statistics;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public abstract class AbstractTypeHandler implements TypeHandler {
    protected final BufferedWriter writer;
    protected final Statistics statistics;

    public AbstractTypeHandler(
            String outputDir,
            String prefix,
            boolean append,
            Statistics statistics,
            String fileName) {
        this.statistics = statistics;
        Path path = Paths.get(outputDir, prefix + fileName);
        try {
            if (append) {
                this.writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } else {
                this.writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void handle(String line) {
        try {
            this.writer.write(line);
            this.writer.newLine();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        this.collectStatistics(line);
    }

    protected abstract void collectStatistics(String line);

    @Override
    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.out.printf("Error closing writer: %s%n", e.getMessage());
        }
    }
}
