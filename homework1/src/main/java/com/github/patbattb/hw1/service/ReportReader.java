package com.github.patbattb.hw1.service;

import java.nio.file.Path;
import java.util.List;

/**
 * Reads data from files.
 */
public final class ReportReader {
    private static final String DIRECTORY_PATH = "reports";
    private static final String FILE_EXTENSION = ".csv";
    private static final String MONTHLY_REPORTS_PREFIX = "m.";
    private static final String YEARLY_REPORTS_PREFIX = "y.";

    private ReportReader() {
    }

    public static List<Path> getMonthlyReports() {
        Path dir = Path.of(DIRECTORY_PATH);
        return FileScanner.getList(dir, MONTHLY_REPORTS_PREFIX, FILE_EXTENSION);
    }

    public static List<Path> getYearlyReports() {
        Path dir = Path.of(DIRECTORY_PATH);
        return FileScanner.getList(dir, YEARLY_REPORTS_PREFIX, FILE_EXTENSION);
    }
}
