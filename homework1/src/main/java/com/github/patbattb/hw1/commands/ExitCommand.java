package com.github.patbattb.hw1.commands;

import com.github.patbattb.hw1.domain.ReportStorage;

/**
 * Exit command. Prints final message.
 */
public final class ExitCommand implements MenuCommand {
    @Override
    public void runCommand(ReportStorage reportStorage) {
        System.out.println("Заврешение работы. До свидания!");
    }
}
