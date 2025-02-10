package ru.nloktionov.handler.impl;

import ru.nloktionov.handler.AbstractTypeHandler;
import ru.nloktionov.statistics.Statistics;

import java.math.BigInteger;

public class IntegerHandler extends AbstractTypeHandler {
    public IntegerHandler(
            String outputDir,
            String prefix,
            boolean append,
            Statistics statistics) {
        super(outputDir, prefix, append, statistics, "integers.txt");
    }

    @Override
    protected void collectStatistics(String line) {
        this.statistics.collectInteger(new BigInteger(line));
    }
}
