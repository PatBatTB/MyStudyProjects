package com.github.patbattb.hw1.commands;

/**
 * Exit command. Prints final message.
 */
public final class ExitCommand implements MenuCommand {
    @Override
    public void run() {
        System.out.println("Заврешение работы. До свидания!");
    }
}
