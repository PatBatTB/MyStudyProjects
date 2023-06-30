package com.github.patbattb.hw1.commands;

import com.github.patbattb.hw1.domain.ReportStorage;
import com.github.patbattb.hw1.service.DataConverter;
import com.github.patbattb.hw1.service.ReportReader;

import java.nio.file.Path;
import java.util.List;

/**
 * Reads data from monthly reports.
 */
public final class ReadMonthlyReportsCommand implements MenuCommand {
    @Override
    public void runCommand(ReportStorage reportStorage) {
        List<Path> fileList = ReportReader.getMonthlyReports();
        DataConverter.convertMonthData(reportStorage, fileList);
        System.out.println("Месячные отчеты считаны.");
    }
}
