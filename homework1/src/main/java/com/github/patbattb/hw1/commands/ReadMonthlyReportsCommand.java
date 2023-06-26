package com.github.patbattb.hw1.commands;

import com.github.patbattb.hw1.service.DataConverter;
import com.github.patbattb.hw1.service.ReportHandler;
import com.github.patbattb.hw1.service.ReportReader;

import java.nio.file.Path;
import java.util.List;

/**
 * Reads data from monthly reports.
 */
public final class ReadMonthlyReportsCommand implements MenuCommand {
    @Override
    public void runCommand(ReportHandler reportHandler) {
        List<Path> fileList = ReportReader.getMonthlyReports();
        DataConverter.convertMonthData(reportHandler.getReportContainer(), fileList);
    }
}
