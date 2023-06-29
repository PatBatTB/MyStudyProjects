package com.github.patbattb.hw1.domain;

import java.util.ArrayList;

/**
 * Implementation of {@link Report} for monthly reports.
 */
public final class ReportMonthly extends Report {
    private static final int DIVIDER = 100;

    private final int date;
    private final int monthNumber;
    private final ArrayList<TransactionMonthly> transactions;

    public ReportMonthly(int date) {
        this.date = date;
        monthNumber = date % DIVIDER;
        transactions = new ArrayList<>();
    }

    public int getDate() {
        return date;
    }

    public int getMonthNumber() {
        return monthNumber;
    }

    public ArrayList<TransactionMonthly> getTransactions() {
        return transactions;
    }
}
