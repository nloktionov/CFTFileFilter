package ru.nloktionov.handler.impl;

import ru.nloktionov.handler.AbstractTypeHandler;
import ru.nloktionov.statistics.Statistics;

import java.math.BigDecimal;

public class FloatHandler extends AbstractTypeHandler {
    public FloatHandler(
            String outputDir,
            String prefix,
            boolean append,
            Statistics statistics) {
        super(outputDir, prefix, append, statistics, "floats.txt");
    }

    @Override
    protected void collectStatistics(String line) {
        this.statistics.collectFloat(new BigDecimal(line));
    }
}
