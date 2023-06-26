package com.github.patbattb.hw1.service;

import com.github.patbattb.hw1.domain.ReportContainer;
import com.github.patbattb.hw1.domain.ReportMonthly;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DataConverter {
    private DataConverter() {
    }

    public static void convertMonthData(ReportContainer reportContainer, List<Path> fileList) {
        HashMap<Integer, ReportMonthly> storage = reportContainer.getStorageMonthlyReports();
        String regex = "(?<=m.)\\d+(?=.csv)";
        for (Path file : fileList) {
            String fileName = file.toString();
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(fileName);
            if (matcher.find()) {
                int date = Integer.parseInt(matcher.group());
                ReportMonthly reportMonthly = new ReportMonthly(date);
                ParserMonthly.parseData(file, reportMonthly);
                storage.put(date, reportMonthly);
            }
        }
    }
}
