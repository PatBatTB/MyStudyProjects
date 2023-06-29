package com.github.patbattb.hw1.commands;

import com.github.patbattb.hw1.domain.ReportStorage;

/**
 * Command interface.
 */
public interface MenuCommand {
    void runCommand(ReportStorage reportStorage);
}
