package com.github.patbattb.hw1.commands;

import com.github.patbattb.hw1.domain.ReportStorage;

/**
 * Unknown command runs when user enters wrong number.
 */
public final class UnknownCommand implements MenuCommand {
    @Override
    public void runCommand(ReportStorage reportStorage) {
        System.out.println("UnknownCommand");
    }
}
