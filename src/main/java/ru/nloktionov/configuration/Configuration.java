package ru.nloktionov.configuration;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.cli.*;
import ru.nloktionov.statistics.StatisticsMode;

import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class Configuration {
    private final boolean isAppend;
    private final StatisticsMode statisticsMode;
    private final String outputDir;
    private final String prefix;
    private final List<String> files;

    public static Configuration parseConfiguration(String[] args, Options options) throws ParseException {
        DefaultParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        List<String> fileNames = cmd.getArgList();
        if (fileNames.isEmpty()) {
            throw new ParseException("No files provided");
        }

        String outputDir = cmd.getOptionValue("o", "");
        String prefix = cmd.getOptionValue("p", "");
        boolean isAppend = cmd.hasOption("a");
        boolean isShortStats = cmd.hasOption("s");
        boolean isFullStats = cmd.hasOption("f");

        StatisticsMode statisticsMode;
        if (isFullStats) {
            statisticsMode = StatisticsMode.FULL;
        } else if (isShortStats) {
            statisticsMode = StatisticsMode.SHORT;
        } else {
            statisticsMode = StatisticsMode.NONE;
        }

        return new Configuration(isAppend, statisticsMode, outputDir, prefix, fileNames);
    }
}
