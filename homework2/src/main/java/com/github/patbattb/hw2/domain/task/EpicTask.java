package com.github.patbattb.hw2.domain.task;

import com.github.patbattb.hw2.domain.TaskStatus;
import com.github.patbattb.hw2.domain.TaskType;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * An inheritor of {@link Task}.
 * A complex task than contains a list of {@link SubTask}.
 */
public final class EpicTask extends Task {
    private final HashMap<Integer, SubTask> subTasks;
    private final TaskType type;

    public EpicTask(String title, String description) {
        super(title, description);
        this.type = TaskType.EPIC;
        this.subTasks = new HashMap<>();
    }

    public EpicTask(String title, String description, LocalDateTime startTime, Duration duration) {
        super(title, description, startTime, duration);
        this.type = TaskType.EPIC;
        this.subTasks = new HashMap<>();
    }

    private EpicTask(Updater updater) {
        super(updater);
        this.type = updater.type;
        this.subTasks = updater.subTasks;
    }

    private EpicTask(int id, String title, String description, TaskStatus taskStatus) {
        super(id, title, description, taskStatus);
        this.type = TaskType.EPIC;
        this.subTasks = new HashMap<>();
    }

    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public String toString() {
        return String.join(",",
                String.valueOf(getId()), type.name(), getTitle(), getTaskStatus().name(), getDescription());
    }

    public static EpicTask fromString(List<String> dataList) {
        int id = Integer.parseInt(dataList.get(0));
        String title = dataList.get(2);
        TaskStatus status = TaskStatus.valueOf(dataList.get(3));
        String description = dataList.get(4);
        return new EpicTask(id, title, description, status);
    }

    //TODO убрать сеттеры, которые должны подтягиваться из сабтасков.
    public static final class Updater extends Task.Updater {
        private final HashMap<Integer, SubTask> subTasks;

        public Updater(EpicTask task) {
            this.id = task.getId();
            this.title = task.getTitle();
            this.type = task.type;
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
