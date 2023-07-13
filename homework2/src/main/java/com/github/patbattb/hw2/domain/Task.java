package com.github.patbattb.hw2.domain;

import com.github.patbattb.hw2.service.IdProvider;

/**
 * A base class for task.
 */
public class Task {

    private final int id;
    private final String title;
    private final String description;
    private final TaskStatus taskStatus;

    public Task(String title, String description) {
        this.id = IdProvider.getNewId();
        this.title = title;
        this.description = description;
        this.taskStatus = TaskStatus.NEW;
    }

    public Task(int id, String title, String description, TaskStatus taskStatus) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.taskStatus = taskStatus;
    }

    /**
     * Getter.
     *
     * @return unique ID of task.
     */
    public int getId() {
        return id;
    }

    /**
     * Getter.
     *
     * @return task's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter.
     *
     * @return task's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter.
     *
     * @return task's status (NEW, IN_PROGRESS, DONE).
     */
    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    /**
     * Overridden method toString.
     *
     * @return String representation of the field values.
     */
    @Override
    public String toString() {
        return "Task{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", description='" + description + '\''
                + ", taskStatus=" + taskStatus
                + '}';
    }
}
