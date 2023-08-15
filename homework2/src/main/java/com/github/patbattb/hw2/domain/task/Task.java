package com.github.patbattb.hw2.domain.task;

import com.github.patbattb.hw2.domain.TaskStatus;
import com.github.patbattb.hw2.domain.TaskType;
import com.github.patbattb.hw2.service.IdProvider;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * A base class for task.
 */
public sealed class Task permits EpicTask, SubTask {

    private final int id;
    private final TaskType type;
    private final String title;
    private final String description;
    private final TaskStatus taskStatus;
    private final LocalDateTime startTime;
    private final Duration duration;
    private final LocalDateTime endTime;

    public Task(String title, String description) {
        this.id = IdProvider.getNewId();
        this.type = TaskType.TASK;
        this.title = title;
        this.description = description;
        this.taskStatus = TaskStatus.NEW;
        this.startTime = null;
        this.duration = null;
        this.endTime = null;
    }

    public Task(String title, String description, LocalDateTime startTime, Duration duration) {
        this.id = IdProvider.getNewId();
        this.type = TaskType.TASK;
        this.title = title;
        this.description = description;
        this.taskStatus = TaskStatus.NEW;
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = startTime.plus(duration);
    }

    protected Task(Updater updater) {
        this.id = updater.id;
        this.type = updater.type;
        this.title = updater.title;
        this.description = updater.description;
        this.taskStatus = updater.taskStatus;
        this.startTime = updater.startTime;
        this.duration = updater.duration;
        this.endTime = Objects.isNull(startTime) ? null : startTime.plus(duration);
    }

    protected Task(int id, String title, String description,
                   TaskStatus taskStatus, LocalDateTime startTime, Duration duration) {
        this.id = id;
        this.type = TaskType.TASK;
        this.title = title;
        this.description = description;
        this.taskStatus = taskStatus;
        this.startTime = startTime;
        this.duration = duration;
        this.endTime = Objects.isNull(startTime) ? null : startTime.plus(duration);
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
     * Getter.
     *
     * @return Start time of the task.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Getter.
     *
     * @return Duration of the task.
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Getter.
     *
     * @return End time of the task.
     */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public static Task fromString(List<String> dataList) {
        int id = Integer.parseInt(dataList.get(0));
        String title = dataList.get(2);
        TaskStatus status = TaskStatus.valueOf(dataList.get(3));
        String description = dataList.get(4);
        LocalDateTime startTime = "null".equals(dataList.get(5)) ? null : LocalDateTime.parse(dataList.get(5));
        Duration duration = "null".equals(dataList.get(6)) ? null : Duration.parse(dataList.get(6));
        return new Task(id, title, description, status, startTime, duration);
    }

    /**
     * Overridden method toString.
     *
     * @return String representation of the field values.
     */
    @Override
    public String toString() {
        return String.join(",",
                String.valueOf(id), type.name(), title, taskStatus.name(),
                description, String.valueOf(startTime), String.valueOf(duration));
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
        protected LocalDateTime startTime;
        protected Duration duration;
        protected LocalDateTime endTime;

        protected Updater() {
        }

        public Updater(Task task) {
            this.id = task.id;
            this.type = task.type;
            this.title = task.title;
            this.description = task.description;
            this.taskStatus = task.taskStatus;
            this.startTime = task.startTime;
            this.duration = task.duration;
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
         * Sets new start time of the task.
         *
         * @param startTime a new time of start.
         */
        public Updater setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }


        /**
         * Sets new duration of the task.
         *
         * @param duration new value of duration.
         */
        public Updater setDuration(Duration duration) {
            this.duration = duration;
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
