package com.github.patbattb.taskmanager.backend.domain.task;

import com.github.patbattb.taskmanager.backend.domain.TaskStatus;
import com.github.patbattb.taskmanager.backend.domain.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * An inheritor of {@link Task}.
 * A part of {@link EpicTask}.
 */
public final class SubTask extends Task {
    private final EpicTask parentEpicTask;

    public SubTask(String title, String description, EpicTask parentEpicTask) {
        super(title, description, TaskType.SUBTASK);
        this.parentEpicTask = parentEpicTask;
    }

    public SubTask(String title, String description,
                   LocalDateTime startTime, Duration duration, EpicTask parentEpicTask) {
        super(title, description, TaskType.SUBTASK, startTime, duration);
        this.parentEpicTask = parentEpicTask;
    }

    private SubTask(Updater updater) {
        super(updater);
        this.parentEpicTask = updater.parentEpicTask;
    }

    private SubTask(int id, String title, String description, TaskStatus taskStatus,
                    LocalDateTime startTime, Duration duration, EpicTask parentEpicTask) {
        super(id, title, description, TaskType.SUBTASK, taskStatus, startTime, duration);
        this.parentEpicTask = parentEpicTask;
    }

    public static SubTask fromString(List<String> dataList, EpicTask epic) {
        int id = Integer.parseInt(dataList.get(0));
        String title = dataList.get(2);
        TaskStatus status = TaskStatus.valueOf(dataList.get(3));
        String description = dataList.get(4);
        LocalDateTime startTime = "null".equals(dataList.get(5)) ? null : LocalDateTime.parse(dataList.get(5));
        Duration duration = "null".equals(dataList.get(6)) ? null : Duration.parse(dataList.get(6));
        return new SubTask(id, title, description, status, startTime, duration, epic);
    }

    public EpicTask getParentEpicTask() {
        return parentEpicTask;
    }

    @Override
    public String toString() {
        return String.join(",", super.toString(), String.valueOf(parentEpicTask.getId()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SubTask subTask = (SubTask) o;

        return Objects.equals(parentEpicTask, subTask.parentEpicTask);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (parentEpicTask != null ? parentEpicTask.hashCode() : 0);
        return result;
    }

    public static final class Updater extends Task.Updater {
        private EpicTask parentEpicTask;

        public Updater(SubTask subTask) {
            this.id = subTask.getId();
            this.title = subTask.getTitle();
            this.type = subTask.getType();
            this.description = subTask.getDescription();
            this.taskStatus = subTask.getTaskStatus();
            this.parentEpicTask = subTask.getParentEpicTask();
            this.startTime = subTask.getStartTime();
            this.duration = subTask.getDuration();
        }

        @Override
        public Updater setTitle(String title) {
            this.title = title;
            return this;
        }

        @Override
        public Updater setDescription(String description) {
            this.description = description;
            return this;
        }

        @Override
        public Updater setTaskStatus(TaskStatus taskStatus) {
            this.taskStatus = taskStatus;
            return this;
        }

        public Updater setParentEpicTask(EpicTask parentEpicTask) {
            this.parentEpicTask = parentEpicTask;
            return this;
        }

        @Override
        public Updater setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        @Override
        public Updater setDuration(Duration duration) {
            this.duration = duration;
            return this;
        }

        public SubTask update() {
            return new SubTask(this);
        }
    }
}
