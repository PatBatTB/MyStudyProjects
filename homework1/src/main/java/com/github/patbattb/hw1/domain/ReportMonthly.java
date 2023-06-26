package com.github.patbattb.hw1.domain;

import java.util.ArrayList;

/**
 * Implementation of {@link Report} for monthly reports.
 */
public final class ReportMonthly extends Report {
    private final int date;
    private final ArrayList<TransactionMonthly> transactions;

    public ReportMonthly(int date) {
        this.date = date;
        transactions = new ArrayList<>();
    }

    public int getDate() {
        return date;
    }

    public ArrayList<TransactionMonthly> getTransactions() {
        return transactions;
    }
}
