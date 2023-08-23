package com.github.patbattb.taskmanager.backend.manager.taskmanager;

import com.github.patbattb.taskmanager.backend.task.domain.EpicTask;
import com.github.patbattb.taskmanager.backend.task.domain.SubTask;
import com.github.patbattb.taskmanager.backend.task.domain.Task;

import java.util.List;

/**
 * The interface contains methods for working with tasks.
 */
public interface TaskManager {

    List<Task> getListOfAllTasks();

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
    Task removeTask(int id);

    void removeAllTasks();

    void addTask(Task... tasks);

    void addTask(Task task);

    void updateTask(Task task);

    /**
     * Get history list of last viewed tasks.
     *
     * @return {@code List<Task>} of last viewed tasks.
     */
    List<Task> history();

    /**
     * Get list of tasks in the natural order of {@link Task#startTime}
     */
    List<Task> getPrioritizedTasks();
}
