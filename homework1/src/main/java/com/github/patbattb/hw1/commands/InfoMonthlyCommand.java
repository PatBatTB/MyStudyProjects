package com.github.patbattb.hw1.commands;

import com.github.patbattb.hw1.domain.ReportMonthly;
import com.github.patbattb.hw1.domain.ReportStorage;

import java.util.HashMap;
import java.util.Map;

/**
 * Prints monthly information.
 */
public final class InfoMonthlyCommand implements MenuCommand {
    @Override
    public void runCommand(ReportStorage reportStorage) {
        HashMap<Integer, ReportMonthly> reports = reportStorage.getReportContainer().getStorageMonthlyReports();

        if (reports.size() == 0) {
            System.out.println("Отсутствуют месячные отчеты!");
            return;
        }

        for (Map.Entry<Integer, ReportMonthly> report : reports.entrySet()) {
            System.out.println(report.getValue().getMonthName() + ":");
            System.out.println("Самый прибыльный товар: "
                    + report.getValue().getMostProfitableProduct().getName()
                    + ", "
                    + report.getValue().getMostProfitableProduct().getSum());
            System.out.println("Самая большая трата: "
                    + report.getValue().getMostExpense().getName()
                    + ", "
                    + report.getValue().getMostExpense().getSum());
            System.out.println();
        }
    }
}
