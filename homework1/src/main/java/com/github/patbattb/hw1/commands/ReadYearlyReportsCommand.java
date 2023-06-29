package com.github.patbattb.hw1.commands;

import com.github.patbattb.hw1.domain.ReportStorage;
import com.github.patbattb.hw1.service.DataConverter;
import com.github.patbattb.hw1.service.ReportReader;

import java.nio.file.Path;
import java.util.List;

/**
 * Reads data from yearly reports.
 */
public final class ReadYearlyReportsCommand implements MenuCommand {
    @Override
    public void runCommand(ReportStorage reportStorage) {
        List<Path> fileList = ReportReader.getYearlyReports();
        DataConverter.convertYearlyData(reportStorage, fileList);
    }
}
