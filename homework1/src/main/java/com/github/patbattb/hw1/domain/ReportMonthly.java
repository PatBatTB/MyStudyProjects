package com.github.patbattb.hw1.domain;

import com.github.patbattb.hw1.service.MonthNameConverter;

import java.util.ArrayList;

/**
 * Implementation of {@link Report} for monthly reports.
 */
public final class ReportMonthly extends Report {
    private static final int DIVIDER = 100;

    private final int date;
    private final int monthNumber;
    private final String monthName;
    private final ArrayList<TransactionMonthly> transactions;
    private HighestValue mostProfitableProduct;
    private HighestValue mostExpense;

    public ReportMonthly(int date) {
        this.date = date;
        monthNumber = date % DIVIDER;
        monthName = MonthNameConverter.convert(monthNumber);
        transactions = new ArrayList<>();
    }

    public int getDate() {
        return date;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public String getMonthName() {
        return monthName;
    }

    public HighestValue getMostProfitableProduct() {
        return mostProfitableProduct;
    }

    public void calculateMostProfitableProduct() {
        String name = null;
        int value = 0;

        for (TransactionMonthly transaction : transactions) {
            if (!transaction.isExpense()) {
                int sum = transaction.getSumOfOne() * transaction.getQuantity();
                if (name == null) {
                    name = transaction.getItemName();
                    value = sum;
                } else {
                    if (sum > value) {
                        name = transaction.getItemName();
                        value = sum;
                    }
                }
            }
        }

        mostProfitableProduct = new HighestValue(name, value);
    }

    public HighestValue getMostExpense() {
        return mostExpense;
    }

    public void calculateMostExpense() {
        String name = null;
        int value = 0;

        for (TransactionMonthly transaction : transactions) {
            if (transaction.isExpense()) {
                int sum = transaction.getSumOfOne() * transaction.getQuantity();
                if (name == null) {
                    name = transaction.getItemName();
                    value = sum;
                } else {
                    if (sum > value) {
                        name = transaction.getItemName();
                        value = sum;
                    }
                }
            }
        }

        mostExpense = new HighestValue(name, value);
    }

    public ArrayList<TransactionMonthly> getTransactions() {
        return transactions;
    }


    public static final class HighestValue {
        private final String name;
        private final int sum;

        private HighestValue(String name, int sum) {
            this.name = name;
            this.sum = sum;
        }

        public String getName() {
            return name;
        }

        public int getSum() {
            return sum;
        }
    }
}
