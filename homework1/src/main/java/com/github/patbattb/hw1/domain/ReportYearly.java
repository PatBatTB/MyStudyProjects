package com.github.patbattb.hw1.domain;

import java.util.ArrayList;

/**
 * Implementation of {@link Report} for yearly reports.
 */
public final class ReportYearly extends Report {
    private final int date;
    private final ArrayList<TransactionYearly> transactions;

    public ReportYearly(int date) {
        this.date = date;
        transactions = new ArrayList<>();
    }

    public int getDate() {
        return date;
    }

    public ArrayList<TransactionYearly> getTransactions() {
        return transactions;
    }
}
