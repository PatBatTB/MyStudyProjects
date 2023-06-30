package com.github.patbattb.hw1.service;

import com.github.patbattb.hw1.domain.ReportMonthly;
import com.github.patbattb.hw1.domain.ReportStorage;
import com.github.patbattb.hw1.domain.ReportYearly;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class DataConverter {
    private DataConverter() {
    }

    public static void convertMonthData(ReportStorage reportStorage, List<Path> fileList) {
        HashMap<Integer, ReportMonthly> reportMap = reportStorage.getReportContainer().getStorageMonthlyReports();
        HashMap<Integer, Integer> sumMonthlyMap = reportStorage.getSumContainer().getSumsFromMonthlyReports();

        sumMonthlyMap.clear();

        String regex = "(?<=m.)\\d+(?=.csv)";
        for (Path file : fileList) {
            String fileName = file.getFileName().toString();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(fileName);
            if (matcher.find()) {
                int date = Integer.parseInt(matcher.group());
                ReportMonthly reportMonthly = CreationReportService.createMonthlyReport(file, date);
                reportMonthly.calculateMostProfitableProduct();
                reportMonthly.calculateMostExpense();
                reportMap.put(date, reportMonthly);
                SumParser.parse(sumMonthlyMap, reportMonthly);
            }
        }
    }

    public static void convertYearlyData(ReportStorage reportStorage, List<Path> fileList) {
        HashMap<Integer, ReportYearly> reportMap = reportStorage.getReportContainer().getStorageYearlyReports();
        HashMap<Integer, Integer> sumYearlyMap = reportStorage.getSumContainer().getSumsFromYearlyReports();

        sumYearlyMap.clear();

        String regex = "(?<=y.)\\d+(?=.csv)";
        for (Path file : fileList) {
            String fileName = file.getFileName().toString();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(fileName);
            if (matcher.find()) {
                int date = Integer.parseInt(matcher.group());
                ReportYearly reportYearly = CreationReportService.createYearlyReport(file, date);
                reportYearly.calculateAverageProfit();
                reportYearly.calculateAverageExpense();
                reportMap.put(date, reportYearly);
                SumParser.parse(sumYearlyMap, reportYearly);
            }
        }
    }
}
