package com.github.patbattb.hw1.domain;

public final class TransactionYearly {
    private final int month;
    private final int amount;
    private final boolean isExpense;

    public TransactionYearly(int month, int amount, boolean isExpense) {
        this.month = month;
        this.amount = amount;
        this.isExpense = isExpense;
    }

    public int getMonth() {
        return month;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isExpense() {
        return isExpense;
    }
}
