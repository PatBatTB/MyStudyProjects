package com.github.patbattb.hw2.domain;

/**
 * A base class for task.
 */
public class Task {

    private int id;
    private String title;
    private String description;
    private TaskStatus taskStatus;

    public Task(String title, String description) {
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
     * ID doesn't can be set manually. The param must be sets in the addTask() method of {@link Manager} class.
     *
     * @param id unique ID of task.
     */
    public void setId(int id) {
        this.id = id;
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
