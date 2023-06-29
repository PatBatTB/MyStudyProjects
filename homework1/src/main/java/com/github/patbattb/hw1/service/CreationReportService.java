package com.github.patbattb.hw1.service;

import com.github.patbattb.hw1.domain.ReportMonthly;
import com.github.patbattb.hw1.domain.ReportYearly;
import com.github.patbattb.hw1.domain.TransactionMonthly;
import com.github.patbattb.hw1.domain.TransactionYearly;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class CreationReportService {
    private CreationReportService() {
    }

    public static ReportMonthly createMonthlyReport(Path file, int date) {
        List<String> lines;
        ReportMonthly reportMonthly = new ReportMonthly(date);

        try {
            lines = Files.readAllLines(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] values = line.split(",");
            String itemName = values[0];
            boolean isExpense = "TRUE".equals(values[1]);
            int quantity = Integer.parseInt(values[2]);
            int sumOfOne = Integer.parseInt(values[3]);
            TransactionMonthly transaction = new TransactionMonthly(itemName, isExpense, quantity, sumOfOne);
            reportMonthly.getTransactions().add(transaction);
        }

        return reportMonthly;
    }

    public static ReportYearly createYearlyReport(Path file, int date) {
        List<String> lines;
        ReportYearly reportYearly = new ReportYearly(date);

        try {
            lines = Files.readAllLines(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] values = line.split(",");
            int month = Integer.parseInt(values[0]);
            int amount = Integer.parseInt(values[1]);
            boolean isExpense = "TRUE".equals(values[2]);
            TransactionYearly transactionYearly = new TransactionYearly(month, amount, isExpense);
            reportYearly.getTransactions().add(transactionYearly);
        }

        return reportYearly;
    }
}
