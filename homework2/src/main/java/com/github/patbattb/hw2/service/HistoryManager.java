package com.github.patbattb.hw2.service;

import com.github.patbattb.hw2.domain.task.Task;

import java.util.List;

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
     * Returns {@link List} of last viewed tasks.
     *
     * @return {@code List<Task>} of last viewed tasks.
     */
    List<Task> getHistory();
}
