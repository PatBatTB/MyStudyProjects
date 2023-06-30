package com.github.patbattb.hw1.commands;

import com.github.patbattb.hw1.domain.ReportStorage;

import java.util.HashMap;
import java.util.Map;

/**
 * Compares data between monthly and yearly reports and prints result.
 */
public final class VerifyReportsCommand implements MenuCommand {
    @Override
    public void runCommand(ReportStorage reportStorage) {
        HashMap<Integer, Integer> sumFromMonthlyReport = reportStorage.getSumContainer().getSumsFromMonthlyReports();
        HashMap<Integer, Integer> sumFromYearlyReport = reportStorage.getSumContainer().getSumsFromYearlyReports();

        if (sumFromYearlyReport.size() == 0 || sumFromMonthlyReport.size() == 0) {
            System.out.println("Отчеты отсутствуют!");
            return;
        }

        int counterError = 0;
        for (Map.Entry<Integer, Integer> record : sumFromYearlyReport.entrySet()) {
            int currentMonth = record.getKey();
            int balanceFromYearlyReport = record.getValue();
            int balanceFromMonthlyReport;
            balanceFromMonthlyReport = sumFromMonthlyReport.getOrDefault(currentMonth, 0);
            if (balanceFromYearlyReport != balanceFromMonthlyReport) {
                System.out.printf("Несоответствие суммы между годовым и месячным отчетом за %d-й месяц.\n",
                        currentMonth);
                counterError++;
            }
        }
        if (counterError == 0) {
            System.out.println("Сверка отчетов завершена. Ошибок не найдено.");
        }
    }
}
