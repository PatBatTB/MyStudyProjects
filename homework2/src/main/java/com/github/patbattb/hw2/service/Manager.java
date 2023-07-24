package com.github.patbattb.hw2.service;

import com.github.patbattb.hw2.domain.task.EpicTask;
import com.github.patbattb.hw2.domain.task.SubTask;
import com.github.patbattb.hw2.domain.task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * The interface contains methods for working with tasks.
 */
public interface Manager {

    ArrayList<Task> getListOfAllTasks();

    List<Task> getListOfOrdinaryTasks();

    List<EpicTask> getListOfEpicTasks();

    List<SubTask> getListOfSubTasks(EpicTask epic);

    /**
     * Get task from container for ID.
     *
     * @param id Task's ID.
     * @return Task or null (if the task doesn't exist).
     */
    Task getTask(int id);

    /**
     * Remove task from container for ID.
     *
     * @param id task's ID.
     */
    void removeTask(int id);

    void removeAllTasks();

    void addTask(Task task);

    void updateTask(Task task);
}
