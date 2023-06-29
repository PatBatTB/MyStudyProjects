package com.github.patbattb.hw1.domain;

/**
 * Service for handling reports.
 */
public final class ReportStorage {
    private final ReportContainer reportContainer;
    private final SumContainer sumContainer;

    public ReportStorage() {
        reportContainer = new ReportContainer();
        sumContainer = new SumContainer();
    }

    public ReportContainer getReportContainer() {
        return reportContainer;
    }

    public SumContainer getSumContainer() {
        return sumContainer;
    }
}
