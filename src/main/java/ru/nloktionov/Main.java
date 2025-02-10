package ru.nloktionov;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import ru.nloktionov.configuration.Configuration;
import ru.nloktionov.statistics.Statistics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Options options = createOptions();

        try {
            Configuration configuration = Configuration.parseConfiguration(args, options);
            ensureOutputDirectoryExists(configuration.getOutputDir());
            FileFilter fileFilter = new FileFilter(
                    configuration.getOutputDir(),
                    configuration.getPrefix(),
                    configuration.isAppend(),
                    configuration.getStatisticsMode(),
                    new Statistics()
            );
            fileFilter.filterFilse(configuration.getFiles());
        } catch (ParseException e) {
            System.out.println("Error parsing args: " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("CFTFileFilter.jar <file>", options);
        } catch (IOException e) {
            System.err.println("Error processing files: " + e.getMessage());
        }
    }

    private static Options createOptions() {
        Options options = new Options();
        options.addOption("o", true, "Output directory");
        options.addOption("p", true, "Prefix");
        options.addOption("a", false, "Append to files");
        options.addOption("s", false, "Short statistics");
        options.addOption("f", false, "Full statistics");
        return options;
    }

    private static void ensureOutputDirectoryExists(String outputDir) throws IOException {
        Path outputPath = Paths.get(outputDir);
        if (!Files.exists(outputPath)) {
            Files.createDirectories(outputPath);
        }
    }
}