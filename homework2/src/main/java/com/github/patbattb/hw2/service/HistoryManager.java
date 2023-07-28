package com.github.patbattb.hw2.service;

import com.github.patbattb.hw2.domain.task.Task;

import java.util.ArrayList;

/**
 * Interface for managing the history of viewed tasks.
 */
public interface HistoryManager {
    /**
     * Add the task to History list.
     *
     * @param task Task to add.
     */
    void add(Task task);

    /**
     * Remove the task from History for id.
     *
     * @param id ID of the task.
     */
    void remove(int id);

    /**
     * Returns {@link ArrayList} of last viewed tasks.
     *
     * @return {@code ArrayList<Task>} of last viewed tasks.
     */
    ArrayList<Task> getHistory();
}
