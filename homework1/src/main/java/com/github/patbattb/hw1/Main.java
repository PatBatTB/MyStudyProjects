package com.github.patbattb.hw1;

import com.github.patbattb.hw1.service.MainMenu;

/**
 * Accounting program's main class.
 * Starts the main menu.
 */
public final class Main {

    private static final MainMenu MENU = new MainMenu();

    private Main() { }

    public static void main(String[] args) {
        MENU.run();
    }
}
