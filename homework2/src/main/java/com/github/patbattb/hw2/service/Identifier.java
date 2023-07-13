package com.github.patbattb.hw2.service;

/**
 * Service for providing ID.
 */
public final class Identifier {
    private static int id = 0;

    private Identifier() {
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
