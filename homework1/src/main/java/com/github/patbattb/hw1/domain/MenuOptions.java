package com.github.patbattb.hw1.domain;

/**
 * Enumeration of available options in the main menu.
 */
public enum MenuOptions {
    READ_MONTHLY_REPORTS("1", "Считать месячные отчеты."),
    READ_YEARLY_REPORTS("2", "Считать годовые отчеты."),
    COMPARE_REPORTS("3", "Сверить отчеты."),
    INFO_MONTHLY("4", "Вывести информацию по месяцам."),
    INFO_YEARLY("5", "Вывести годовую информацию."),
    EXIT("0", "Выход из программы.");

    private final String number;
    private final String description;

    MenuOptions(String number, String description) {
        this.number = number;
        this.description = description;
    }

    public String getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }
}
