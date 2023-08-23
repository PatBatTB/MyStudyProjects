package com.github.patbattb.taskmanager.backend.manager.historymanager;

import com.github.patbattb.taskmanager.backend.task.domain.Task;

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
     * Remove the task from History for id.
     *
     * @param id ID of the task.
     */
    void remove(int id);

    /**
     * Returns {@link List} of the last viewed tasks in order from newest to oldest.
     *
     * @return {@link List<Task>} of last viewed tasks.
     */
    List<Task> getHistory();
}
