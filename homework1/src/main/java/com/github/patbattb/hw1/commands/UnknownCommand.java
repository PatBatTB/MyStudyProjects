package com.github.patbattb.hw1.commands;

/**
 * Unknown command runs when user enters wrong number.
 */
public final class UnknownCommand implements MenuCommand {
    @Override
    public void run() {
        System.out.println("UnknownCommand");
    }
}
