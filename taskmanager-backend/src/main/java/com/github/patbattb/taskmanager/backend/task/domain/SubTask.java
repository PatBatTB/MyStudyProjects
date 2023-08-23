package com.github.patbattb.taskmanager.backend.task.domain;

import com.github.patbattb.taskmanager.backend.task.TaskStatus;
import com.github.patbattb.taskmanager.backend.task.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * An inheritor of {@link Task}.
 * A part of {@link EpicTask}.
 */
public final class SubTask extends Task {
    private final int parentEpicTaskId;

    public SubTask(String title, String description, int parentEpicTaskId) {
        super(title, description, TaskType.SUBTASK);
        this.parentEpicTaskId = parentEpicTaskId;
    }

    public SubTask(String title, String description,
                   LocalDateTime startTime, Duration duration, int parentEpicTaskId) {
        super(title, description, TaskType.SUBTASK, startTime, duration);
        this.parentEpicTaskId = parentEpicTaskId;
    }

    private SubTask(Updater updater) {
        super(updater);
        this.parentEpicTaskId = updater.parentEpicTaskId;
    }

    private SubTask(int id, String title, String description, TaskStatus taskStatus,
                    LocalDateTime startTime, Duration duration, int parentEpicTaskId) {
        super(id, title, description, TaskType.SUBTASK, taskStatus, startTime, duration);
        this.parentEpicTaskId = parentEpicTaskId;
    }

    public static SubTask fromString(List<String> dataList) {
        int id = Integer.parseInt(dataList.get(0));
        String title = dataList.get(2);
        TaskStatus status = TaskStatus.valueOf(dataList.get(3));
        String description = dataList.get(4);
        LocalDateTime startTime = "null".equals(dataList.get(5)) ? null : LocalDateTime.parse(dataList.get(5));
        Duration duration = "null".equals(dataList.get(6)) ? null : Duration.parse(dataList.get(6));
        int epicId = Integer.parseInt(dataList.get(7));
        return new SubTask(id, title, description, status, startTime, duration, epicId);
    }

    public int getParentEpicTaskId() {
        return parentEpicTaskId;
    }

    @Override
    public String toString() {
        return String.join(",", super.toString(), String.valueOf(parentEpicTaskId));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        SubTask subTask = (SubTask) o;

        return parentEpicTaskId == subTask.parentEpicTaskId;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + parentEpicTaskId;
        return result;
    }

    public static final class Updater extends Task.Updater {
        private int parentEpicTaskId;

        public Updater(SubTask subTask) {
            this.id = subTask.getId();
            this.title = subTask.getTitle();
            this.type = subTask.getType();
            this.description = subTask.getDescription();
            this.taskStatus = subTask.getTaskStatus();
            this.parentEpicTaskId = subTask.getParentEpicTaskId();
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

        public Updater setParentEpicTaskId(int parentEpicTaskId) {
            this.parentEpicTaskId = parentEpicTaskId;
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
