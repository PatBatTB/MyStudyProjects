package com.github.patbattb.hw1.domain;

import com.github.patbattb.hw1.commands.*;
import com.google.common.collect.ImmutableMap;

/**
 * Map with command classes and corresponding enums.
 */
public final class CommandMap {

    private final ImmutableMap<String, MenuCommand> mapOfCommands;
    private final MenuCommand defaultCommand;

    public CommandMap() {
        mapOfCommands = ImmutableMap.<String, MenuCommand>builder()
                .put(MenuOptions.READ_MONTHLY_REPORTS.getNumber(), new ReadMonthlyReportsCommand())
                .put(MenuOptions.READ_YEARLY_REPORTS.getNumber(), new ReadYearlyReportsCommand())
                .put(MenuOptions.COMPARE_REPORTS.getNumber(), new VerifyReportsCommand())
                .put(MenuOptions.INFO_MONTHLY.getNumber(), new InfoMonthlyCommand())
                .put(MenuOptions.INFO_YEARLY.getNumber(), new InfoYearlyCopmmand())
                .put(MenuOptions.EXIT.getNumber(), new ExitCommand())
                .build();

        defaultCommand = new UnknownCommand();
    }

    public void execute(String number, ReportStorage reportStorage) {
        mapOfCommands.getOrDefault(number, defaultCommand).runCommand(reportStorage);
    }
}
