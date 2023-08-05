package com.github.patbattb.hw2.domain.task;

import com.github.patbattb.hw2.domain.TaskStatus;
import com.github.patbattb.hw2.domain.TaskType;

import java.util.List;

/**
 * An inheritor of {@link Task}.
 * A part of {@link EpicTask}.
 */
public final class SubTask extends Task {
    private final TaskType type;
    private final EpicTask parentEpicTask;

    public SubTask(String title, String description, EpicTask parentEpicTask) {
        super(title, description);
        this.type = TaskType.SUBTASK;
        this.parentEpicTask = parentEpicTask;
    }

    private SubTask(Updater updater) {
        super(updater);
        this.type = updater.type;
        this.parentEpicTask = updater.parentEpicTask;
    }

    private SubTask(int id, String title, String description, TaskStatus taskStatus, EpicTask parentEpicTask) {
        super(id, title, description, taskStatus);
        this.type = TaskType.SUBTASK;
        this.parentEpicTask = parentEpicTask;
    }

    public EpicTask getParentEpicTask() {
        return parentEpicTask;
    }

    @Override
    public String toString() {
        return String.join(",", String.valueOf(getId()), type.name(),
                getTitle(), getTaskStatus().name(), getDescription(), String.valueOf(getParentEpicTask().getId()));
    }

    public static SubTask fromString(List<String> dataList, EpicTask epic) {
        int id = Integer.parseInt(dataList.get(0));
        String title = dataList.get(2);
        TaskStatus status = TaskStatus.valueOf(dataList.get(3));
        String description = dataList.get(4);
        return new SubTask(id, title, description, status, epic);
    }

    public static final class Updater extends Task.Updater {
        private EpicTask parentEpicTask;

        public Updater(SubTask subTask) {
            this.id = subTask.getId();
            this.title = subTask.getTitle();
            this.type = subTask.type;
            this.description = subTask.getDescription();
            this.taskStatus = subTask.getTaskStatus();
            this.parentEpicTask = subTask.getParentEpicTask();
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

        public SubTask update() {
            return new SubTask(this);
        }
    }
}
