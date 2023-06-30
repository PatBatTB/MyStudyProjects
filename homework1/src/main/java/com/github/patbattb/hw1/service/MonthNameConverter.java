package com.github.patbattb.hw1.service;

import com.google.common.collect.ImmutableMap;

/**
 * Convert month's number to month's name.
 */
public final class MonthNameConverter {

    @SuppressWarnings("magicnumber")
    private static final ImmutableMap<Integer, String> MONTH_NAME_MAP = ImmutableMap.<Integer, String>builder()
            .put(1, "Январь")
            .put(2, "Феварль")
            .put(3, "Март")
            .put(4, "Апрель")
            .put(5, "Май")
            .put(6, "Июнь")
            .put(7, "Июль")
            .put(8, "Август")
            .put(9, "Сентябрь")
            .put(10, "Октябрь")
            .put(11, "Ноябрь")
            .put(12, "Декабрь")
            .build();

    private MonthNameConverter() {
    }

    public static String convert(int monthNumber) {
        return MONTH_NAME_MAP.get(monthNumber);
    }
}
