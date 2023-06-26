package com.github.patbattb.hw1.commands;

import com.github.patbattb.hw1.service.ReportHandler;

/**
 * Command interface.
 */
public interface MenuCommand {
    void runCommand(ReportHandler reportHandler);
}
