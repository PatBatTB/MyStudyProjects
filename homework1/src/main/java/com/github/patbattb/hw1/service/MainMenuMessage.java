package com.github.patbattb.hw1.service;

import com.github.patbattb.hw1.domain.MenuOptions;

/**
 * Prints main menu message.
 */
public final class MainMenuMessage {

    private static final String HEADER = """
            Главное меню.
            Доступные команды:
            """;

    private MainMenuMessage() { }

    public static void printMessage() {
        System.out.println();
        System.out.print(HEADER);
        System.out.print(listOfCommans());
        System.out.println("Введите номер команды.");
    }

    private static String listOfCommans() {
        StringBuilder aList = new StringBuilder();
        for (MenuOptions option : MenuOptions.values()) {
            aList.append(option.getNumber())
                    .append(": ")
                    .append(option.getDescription())
                    .append("\n");
        }
        return aList.toString();
    }
}
