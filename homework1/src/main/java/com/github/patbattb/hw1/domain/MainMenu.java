package com.github.patbattb.hw1.domain;

import com.github.patbattb.hw1.service.MainMenuMessage;

import java.util.Scanner;

/**
 * Main menu for accounting application.
 */
public final class MainMenu implements Runnable {

    private final CommandMap commandMap = new CommandMap();
    private final Scanner scin = new Scanner(System.in);
    private String commandNumber;

    @Override
    public void run() {
            do {
                MainMenuMessage.printMessage();
                commandNumber = scin.nextLine();
                commandMap.execute(commandNumber);
            } while (!"0".equals(commandNumber));
    }
}
