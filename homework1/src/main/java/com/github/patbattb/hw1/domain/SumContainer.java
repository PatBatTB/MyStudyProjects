package com.github.patbattb.hw1.domain;

import java.util.HashMap;

/**
 * Contains sums from reports.
 */
public final class SumContainer {
    private final HashMap<Integer, Integer> sumsFromYearlyReports;
    private final HashMap<Integer, Integer> sumsFromMonthlyReports;

    public SumContainer() {
        sumsFromYearlyReports = new HashMap<>();
        sumsFromMonthlyReports = new HashMap<>();
    }

    public HashMap<Integer, Integer> getSumsFromYearlyReports() {
        return sumsFromYearlyReports;
    }

    public HashMap<Integer, Integer> getSumsFromMonthlyReports() {
        return sumsFromMonthlyReports;
    }
}
