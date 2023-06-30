package com.github.patbattb.hw1.commands;

import com.github.patbattb.hw1.domain.ReportStorage;
import com.github.patbattb.hw1.domain.ReportYearly;
import com.github.patbattb.hw1.service.MonthNameConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Prints yearly information.
 */
public final class InfoYearlyCopmmand implements MenuCommand {
    @Override
    public void runCommand(ReportStorage reportStorage) {
        HashMap<Integer, ReportYearly> reports = reportStorage.getReportContainer().getStorageYearlyReports();
        HashMap<Integer, Integer> sums = reportStorage.getSumContainer().getSumsFromYearlyReports();

        if (reports.size() == 0) {
            System.out.println("Отсутствуют годовые отчеты!");
            return;
        }

        for (Map.Entry<Integer, ReportYearly> report : reports.entrySet()) {
            System.out.println("Год: " + report.getKey());
            System.out.println();
            System.out.println("Прибыль по месяцам:");
            for (Map.Entry<Integer, Integer> sum : sums.entrySet()) {
                System.out.println(MonthNameConverter.convert(sum.getKey())
                        + ", "
                        + sum.getValue());
            }
            System.out.println();
            System.out.printf("Средний ежемесячный доход: %.2f\n", report.getValue().getAverageProfit());
            System.out.printf("Средний ежемесячный расход: %.2f\n", report.getValue().getAverageExpense());
        }
    }
}
