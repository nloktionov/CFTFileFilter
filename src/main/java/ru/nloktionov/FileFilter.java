package ru.nloktionov;

import ru.nloktionov.handler.TypeHandler;
import ru.nloktionov.handler.impl.FloatHandler;
import ru.nloktionov.handler.impl.IntegerHandler;
import ru.nloktionov.handler.impl.StringHandler;
import ru.nloktionov.statistics.Statistics;
import ru.nloktionov.statistics.StatisticsMode;
import ru.nloktionov.type.Type;
import ru.nloktionov.type.TypeResolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class FileFilter {
    private final String outputDir;
    private final String prefix;
    private final boolean append;
    private final StatisticsMode statisticsMode;
    private final Statistics statistics;
    private final Map<Type, TypeHandler> handlers = new HashMap<>();
    private final TypeResolver typeResolver = new TypeResolver();

    public FileFilter(
            String outputDir,
            String prefix,
            boolean append,
            StatisticsMode statisticsMode,
            Statistics statistics
    ) {
        this.outputDir = outputDir;
        this.prefix = prefix;
        this.append = append;
        this.statisticsMode = statisticsMode;
        this.statistics = statistics;
    }

    public void filterFilse(List<String> files) throws IOException {
        try {
            for (String file : files) {
                this.filterFile(file);
            }
            this.printStatistics();
        } finally {
            this.closeHandlers();
        }
    }

    private void filterFile(String file) throws IOException {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(file))) {
            Stream<String> lines = bufferedReader.lines();
            lines.forEach(this::filterLine);
        } catch (NoSuchFileException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    private void filterLine(String line) {
        Type type = this.typeResolver.resolve(line);
        TypeHandler handler = this.getHandler(type);
        handler.handle(line);
    }

    private TypeHandler getHandler(Type type) {
        return this.handlers.computeIfAbsent(type, _ -> switch (type) {
            case INTEGER -> new IntegerHandler(this.outputDir, this.prefix, this.append, this.statistics);
            case FLOAT -> new FloatHandler(this.outputDir, this.prefix, this.append, this.statistics);
            case STRING -> new StringHandler(this.outputDir, this.prefix, this.append, this.statistics);
        });
    }

    private void printStatistics() {
        switch (this.statisticsMode) {
            case FULL -> this.statistics.printFullStatistics();
            case SHORT -> this.statistics.printShortStatistics();
        }
    }

    private void closeHandlers() {
        this.handlers.values().forEach(TypeHandler::close);
    }
}
