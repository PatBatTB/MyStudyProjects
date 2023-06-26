package com.github.patbattb.hw1.service;

import com.github.patbattb.hw1.domain.ReportMonthly;
import com.github.patbattb.hw1.domain.TransactionMonthly;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class ParserMonthly {
    private ParserMonthly() {
    }

    public static void parseData(Path file, ReportMonthly reportMonthly) {
        List<String> lines;
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
    }
}
