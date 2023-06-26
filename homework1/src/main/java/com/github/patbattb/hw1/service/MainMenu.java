package com.github.patbattb.hw1.service;

import com.github.patbattb.hw1.domain.CommandMap;

import java.util.Scanner;

/**
 * Main menu for accounting application.
 */
public final class MainMenu {

    private static final ReportHandler REPORT_HANDLER = new ReportHandler();
    private final CommandMap commandMap = new CommandMap();
    private final Scanner scin = new Scanner(System.in);
    private String commandNumber;


    public void run() {
        do {
            MainMenuMessage.printMessage();
            commandNumber = scin.nextLine();
            commandMap.execute(commandNumber, REPORT_HANDLER);
        } while (!"0".equals(commandNumber));
    }
}
