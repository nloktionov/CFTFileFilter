package ru.nloktionov.statistics;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Statistics {
    private Long integerCount = 0L;
    private Long floatCount = 0L;
    private Long stringCount = 0L;

    private BigInteger minInteger = null;
    private BigInteger maxInteger = null;
    private BigInteger integerSum = BigInteger.ZERO;

    private BigDecimal minFloat = null;
    private BigDecimal maxFloat = null;
    private BigDecimal floatSum = BigDecimal.ZERO;

    private Long minStringLength = Long.MAX_VALUE;
    private Long maxStringLength = 0L;

    public void collectInteger(BigInteger bigInteger) {
        this.integerCount++;
        if (this.maxInteger == null || bigInteger.compareTo(this.maxInteger) > 0) {
            this.maxInteger = bigInteger;
        }
        if (this.minInteger == null || bigInteger.compareTo(this.minInteger) < 0) {
            this.minInteger = bigInteger;
        }
        this.integerSum = this.integerSum.add(bigInteger);
    }

    public void collectFloat(BigDecimal bigDecimal) {
        this.floatCount++;
        if (this.maxFloat == null || bigDecimal.compareTo(this.maxFloat) > 0) {
            this.maxFloat = bigDecimal;
        }
        if (this.minFloat == null || bigDecimal.compareTo(this.minFloat) < 0) {
            this.minFloat = bigDecimal;
        }
        this.floatSum = this.floatSum.add(bigDecimal);
    }

    public void collectString(String string) {
        this.stringCount++;
        int length = string.length();
        this.minStringLength = Math.min(this.minStringLength, length);
        this.maxStringLength = Math.max(this.maxStringLength, length);
    }

    public void printShortStatistics() {
        System.out.println("Integers: " + integerCount);
        System.out.println("Floats: " + floatCount);
        System.out.println("Strings: " + stringCount);
    }

    public void printFullStatistics() {
        this.printShortStatistics();
        if (integerCount > 0) {
            System.out.printf("Integers - Min: %d, Max: %d, Sum: %d, Avg: %.5f%n",
                    this.minInteger,
                    this.maxInteger,
                    this.integerSum,
                    new BigDecimal(this.integerSum).divide(new BigDecimal(this.integerCount), 5, RoundingMode.HALF_UP)
            );
        }
        if (floatCount > 0) {
            System.out.printf("Floats - Min: %.5f, Max: %.5f, Sum: %.5f, Avg: %.5f%n",
                    this.minFloat,
                    this.maxFloat,
                    this.floatSum,
                    this.floatSum.divide(new BigDecimal(this.floatCount), 5, RoundingMode.HALF_UP)
            );
        }
        if (stringCount > 0) {
            System.out.printf("Strings - Min Length: %d, Max Length: %d%n",
                    this.minStringLength,
                    this.maxStringLength
            );
        }
    }
}
