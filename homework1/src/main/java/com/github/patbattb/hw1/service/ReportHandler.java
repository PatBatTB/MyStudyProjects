package com.github.patbattb.hw1.service;

import com.github.patbattb.hw1.domain.ReportContainer;

/**
 * Service for handling reports.
 */
public final class ReportHandler {
    private final ReportContainer reportContainer;

    public ReportHandler() {
        reportContainer = new ReportContainer();
    }

    public ReportContainer getReportContainer() {
        return reportContainer;
    }
}
