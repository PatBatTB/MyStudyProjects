package com.github.patbattb.hw2.service;

/**
 * Service for providing ID.
 */
public final class IdProvider {
    private static int id = 0;

    private IdProvider() {
    }

    /**
     * Provides the following id.
     *
     * @return unique ID number.
     */
    public static int getNewId() {
        return ++id;
    }
}
