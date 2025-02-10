package ru.nloktionov.handler.impl;

import ru.nloktionov.handler.AbstractTypeHandler;
import ru.nloktionov.statistics.Statistics;

public class StringHandler extends AbstractTypeHandler {
    public StringHandler(
            String outputDir,
            String prefix,
            boolean append,
            Statistics statistics) {
        super(outputDir, prefix, append, statistics, "strings.txt");
    }

    @Override
    protected void collectStatistics(String line) {
        this.statistics.collectString(line);
    }
}
