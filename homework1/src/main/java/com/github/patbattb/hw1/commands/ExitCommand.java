package com.github.patbattb.hw1.commands;

import com.github.patbattb.hw1.service.ReportHandler;

/**
 * Exit command. Prints final message.
 */
public final class ExitCommand implements MenuCommand {
    @Override
    public void runCommand(ReportHandler reportHandler) {
        System.out.println("Заврешение работы. До свидания!");
    }
}
