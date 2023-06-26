package com.github.patbattb.hw1.commands;

import com.github.patbattb.hw1.service.ReportHandler;

/**
 * Unknown command runs when user enters wrong number.
 */
public final class UnknownCommand implements MenuCommand {
    @Override
    public void runCommand(ReportHandler reportHandler) {
        System.out.println("UnknownCommand");
    }
}
