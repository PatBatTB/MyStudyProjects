package com.github.patbattb.hw2.domain.task;

import com.github.patbattb.hw2.domain.TaskStatus;
import com.github.patbattb.hw2.domain.TaskType;

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

    public EpicTask getParentEpicTask() {
        return parentEpicTask;
    }

    @Override
    public String toString() {
        return super.toString()
                + "{parentEpicTask.id=" + parentEpicTask.getId()
                + "} ";
    }

    public static final class Updater extends Task.Updater {
        private EpicTask parentEpicTask;

        public Updater(SubTask subTask) {
            this.id = subTask.getId();
            this.title = subTask.getTitle();
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
