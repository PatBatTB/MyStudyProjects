package com.github.patbattb.hw1.service;

import com.github.patbattb.hw1.domain.ReportMonthly;
import com.github.patbattb.hw1.domain.ReportYearly;
import com.github.patbattb.hw1.domain.TransactionMonthly;
import com.github.patbattb.hw1.domain.TransactionYearly;

import java.util.HashMap;
import java.util.List;

public final class SumParser {
    private SumParser() {
    }

    public static void parse(HashMap<Integer, Integer> sumMonthlyMap, ReportMonthly reportMonthly) {
        List<TransactionMonthly> transactions = reportMonthly.getTransactions();
        int monthNumber = reportMonthly.getMonthNumber();
        for (TransactionMonthly transactionMonthly : transactions) {
            int price = transactionMonthly.getSumOfOne();
            int quantity = transactionMonthly.getQuantity();
            int sum = price * quantity;
            if (transactionMonthly.isExpense()) {
                sum *= -1;
            }
            if (sumMonthlyMap.containsKey(monthNumber)) {
                sum += sumMonthlyMap.get(monthNumber);
            }
            sumMonthlyMap.put(monthNumber, sum);
        }
    }

    public static void parse(HashMap<Integer, Integer> sumYearlyMap, ReportYearly reportYearly) {
        List<TransactionYearly> transactions = reportYearly.getTransactions();
        for (TransactionYearly transactionYearly : transactions) {
            int monthNumber = transactionYearly.getMonth();
            int sum = transactionYearly.getAmount();
            if (transactionYearly.isExpense()) {
                sum *= -1;
            }
            if (sumYearlyMap.containsKey(monthNumber)) {
                sum += sumYearlyMap.get(monthNumber);
            }
            sumYearlyMap.put(monthNumber, sum);
        }
    }
}
