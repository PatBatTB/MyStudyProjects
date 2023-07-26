package com.github.patbattb.hw2.service;

/**
 * Util class for create right implementation of {@link TaskManager}.
 */
public final class Managers {
    private Managers() {
    }

    /**
     * Getter for default manager.
     *
     * @return Default implementation of {@link TaskManager}
     */
    public static TaskManager getDefault() {
        return new DefaultTaskManager();
    }

    /**
     * Getter for default history manager.
     *
     * @return Default implementation of {@link HistoryManager}.
     */
    public static HistoryManager getDefaultHistory() {
        return new DefaultHistoryManager();
    }
}
