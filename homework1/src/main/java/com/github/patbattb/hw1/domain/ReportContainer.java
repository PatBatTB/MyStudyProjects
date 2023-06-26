package com.github.patbattb.hw1.domain;

import java.util.HashMap;

/**
 * Contains maps of reports.
 * Monthly map key is a number in format "YYYYMM" where "YYYY" - a number of year, "MM" - a number of month.
 * Yearly map key is a number in format "YYYY" - a number of year.
 */
public final class ReportContainer {

    private final HashMap<Integer, ReportMonthly> storageMonthlyReports = new HashMap<>();
    private final HashMap<Integer, ReportYearly> storageYearlyReports = new HashMap<>();

    public HashMap<Integer, ReportMonthly> getStorageMonthlyReports() {
        return storageMonthlyReports;
    }

    public HashMap<Integer, ReportYearly> getStorageYearlyReports() {
        return storageYearlyReports;
    }
}
