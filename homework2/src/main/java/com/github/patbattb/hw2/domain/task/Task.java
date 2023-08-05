package com.github.patbattb.hw2.domain.task;

import com.github.patbattb.hw2.domain.TaskStatus;
import com.github.patbattb.hw2.domain.TaskType;
import com.github.patbattb.hw2.service.IdProvider;

import java.util.List;

/**
 * A base class for task.
 */
public sealed class Task permits EpicTask, SubTask {

    private final int id;
    private final TaskType type;
    private final String title;
    private final String description;
    private final TaskStatus taskStatus;

    public Task(String title, String description) {
        this.id = IdProvider.getNewId();
        this.type = TaskType.TASK;
        this.title = title;
        this.description = description;
        this.taskStatus = TaskStatus.NEW;
    }

    protected Task(Updater updater) {
        this.id = updater.id;
        this.type = updater.type;
        this.title = updater.title;
        this.description = updater.description;
        this.taskStatus = updater.taskStatus;
    }

    private Task(int id, TaskType type, String title, String description, TaskStatus taskStatus) {
        this.id = id;
        this.type = type;
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
        return String.join(",", String.valueOf(id), type.name(), title, taskStatus.name(), description);
    }

    public static Task fromString(List<String> dataList) {
        int id = Integer.parseInt(dataList.get(0));
        TaskType type = TaskType.valueOf(dataList.get(1));
        String title = dataList.get(2);
        TaskStatus status = TaskStatus.valueOf(dataList.get(3));
        String description = dataList.get(4);
        return new Task(id, type, title, description, status);
    }

    /**
     * Variation of the builder pattern.
     * The constructor accepts the {@link Task}. Setters set new values.
     * At the end, the {@link Updater#update} method returns a new instance of the {@link Task}
     */
    @SuppressWarnings("VisibilityModifier")
    public static class Updater {
        protected int id;
        protected TaskType type;
        protected String title;
        protected String description;
        protected TaskStatus taskStatus;

        protected Updater() {
        }

        public Updater(Task task) {
            this.id = task.id;
            this.type = task.type;
            this.title = task.title;
            this.description = task.description;
            this.taskStatus = task.taskStatus;
        }

        /**
         * Sets new title value.
         *
         * @param title a new value of title;
         * @return this {@code Task.Updater}
         */
        public Updater setTitle(String title) {
            this.title = title;
            return this;
        }

        /**
         * Sets new description value.
         *
         * @param description a new value of description;
         * @return this {@code Task.Updater}
         */
        public Updater setDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * Sets new task status.
         *
         * @param taskStatus a new task status enum.
         * @return this {@code Task.Updater}
         */
        public Updater setTaskStatus(TaskStatus taskStatus) {
            this.taskStatus = taskStatus;
            return this;
        }

        /**
         * Returns a {@code Task} built from the parameters set by the setter methods.
         * If some parameters were not assigned by setters, their values will remain the same.
         *
         * @return updated {@code Task} build.
         */
        public Task update() {
            return new Task(this);
        }
    }
}
