package com.github.patbattb.hw2.service;

import com.github.patbattb.hw2.domain.EpicTask;
import com.github.patbattb.hw2.domain.SubTask;
import com.github.patbattb.hw2.domain.Task;
import com.github.patbattb.hw2.domain.TaskContainer;

import java.util.List;

/**
 * A service for managing tasks.
 * Contains all tasks and methods for working with them.
 */
public final class Manager {

    private final TaskContainer taskContainer;

    public Manager() {
        taskContainer = new TaskContainer();
    }

    public TaskContainer getTaskContainer() {
        return taskContainer;
    }

    public List<Task> getListOfAllTasks() {
        return null;
    }

    public List<Task> getListOfOrdinaryTasks() {
        return null;
    }

    public List<Task> getListOfEpicTasks() {
        return null;
    }

    public List<Task> getListOfSubTasks(EpicTask epic) {
        return null;
    }

    public Task getTask(int id) {
        return null;
    }

    public void removeAllTasks() {

    }

    public <T extends Task> void addTask(T task) {
        if (task.getClass() == EpicTask.class) {
            addEpicTask((EpicTask) task);
        } else if (task.getClass() == SubTask.class) {
            addSubTask((SubTask) task);
        } else {
            taskContainer.getOrdinaryTaskMap().put(task.getId(), task);
        }
    }

    private void addEpicTask(EpicTask task) {
        taskContainer.getEpicTaskMap().put(task.getId(), task);
    }

    private void addSubTask(SubTask task) {
        EpicTask epic = task.getParentEpicTask();
        taskContainer.getSubTaskMap().put(task.getId(), task);
        epic.getSubTasks().add(task);
    }

    public void updateTask(Task task, int id) {

    }

}
