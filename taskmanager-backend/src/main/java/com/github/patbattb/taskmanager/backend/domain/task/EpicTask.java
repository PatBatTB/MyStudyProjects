package com.github.patbattb.taskmanager.backend.domain.task;

import com.github.patbattb.taskmanager.backend.domain.TaskStatus;
import com.github.patbattb.taskmanager.backend.domain.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * An inheritor of {@link Task}.
 * A complex task than contains a list of {@link SubTask}.
 */
public final class EpicTask extends Task {
    private final HashMap<Integer, SubTask> subTasks;

    public EpicTask(String title, String description) {
        super(title, description, TaskType.EPIC);
        this.subTasks = new HashMap<>();
    }

    private EpicTask(Updater updater) {
        super(updater);
        this.subTasks = updater.subTasks;
    }

    private EpicTask(int id, String title, String description, TaskStatus taskStatus,
                     LocalDateTime startTime, Duration duration) {
        super(id, title, description, TaskType.EPIC, taskStatus, startTime, duration);
        this.subTasks = new HashMap<>();
    }

    public static EpicTask fromString(List<String> dataList) {
        int id = Integer.parseInt(dataList.get(0));
        String title = dataList.get(2);
        TaskStatus status = TaskStatus.valueOf(dataList.get(3));
        String description = dataList.get(4);
        LocalDateTime startTime = "null".equals(dataList.get(5)) ? null : LocalDateTime.parse(dataList.get(5));
        Duration duration = "null".equals(dataList.get(6)) ? null : Duration.parse(dataList.get(6));
        return new EpicTask(id, title, description, status, startTime, duration);
    }

    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        EpicTask epicTask = (EpicTask) o;

        return Objects.equals(subTasks, epicTask.subTasks);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (subTasks != null ? subTasks.hashCode() : 0);
        return result;
    }

    public static final class Updater extends Task.Updater {
        private final HashMap<Integer, SubTask> subTasks;

        public Updater(EpicTask task) {
            this.id = task.getId();
            this.title = task.getTitle();
            this.type = task.getType();
            this.description = task.getDescription();
            this.taskStatus = task.getTaskStatus();
            this.subTasks = task.getSubTasks();
            this.startTime = task.getStartTime();
            this.duration = task.getDuration();
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

        @Override
        public EpicTask update() {
            return new EpicTask(this);
        }
    }
}
