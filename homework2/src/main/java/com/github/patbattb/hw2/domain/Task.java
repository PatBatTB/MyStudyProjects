package com.github.patbattb.hw2.domain;

import com.github.patbattb.hw2.service.Identifier;

/**
 * A base class for task.
 */
public class Task {

    private final int id;
    private String title;
    private String description;
    private TaskStatus taskStatus;

    public Task(String title, String description) {
        this.id = Identifier.getNewId();
        this.title = title;
        this.description = description;
        this.taskStatus = TaskStatus.NEW;
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
     * Task's title can be sets when task renaming is needed.
     *
     * @param title task's title.
     */
    public void setTitle(String title) {
        this.title = title;
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
     * Can be sets when task's description change is needed.
     *
     * @param description task's description.
     */
    public void setDescription(String description) {
        this.description = description;
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
     * Sets new task's status.
     *
     * @param taskStatus task's status (NEW, IN_PROGRESS, DONE).
     */
    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}
