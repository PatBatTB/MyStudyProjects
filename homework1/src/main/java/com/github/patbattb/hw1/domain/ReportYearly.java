package com.github.patbattb.hw1.domain;

import java.util.ArrayList;

/**
 * Implementation of {@link Report} for yearly reports.
 */
public final class ReportYearly extends Report {
    private final int date;
    private final ArrayList<TransactionYearly> transactions;
    private double averageProfit;
    private double averageExpense;

    public ReportYearly(int date) {
        this.date = date;
        transactions = new ArrayList<>();
    }

    public int getDate() {
        return date;
    }

    public double getAverageProfit() {
        return averageProfit;
    }

    public void calculateAverageProfit() {
        int sum = 0;
        int counter = 0;
        for (TransactionYearly transaction : transactions) {
            if (!transaction.isExpense()) {
                sum += transaction.getAmount();
                counter++;
            }
        }
        averageProfit = counter == 0 ? 0 : (double) sum / counter;
    }

    public double getAverageExpense() {
        return averageExpense;
    }

    public void calculateAverageExpense() {
        int sum = 0;
        int counter = 0;
        for (TransactionYearly transaction : transactions) {
            if (transaction.isExpense()) {
                sum += transaction.getAmount();
                counter++;
            }
        }
        averageExpense = counter == 0 ? 0 : (double) sum / counter;
    }

    public ArrayList<TransactionYearly> getTransactions() {
        return transactions;
    }
}
