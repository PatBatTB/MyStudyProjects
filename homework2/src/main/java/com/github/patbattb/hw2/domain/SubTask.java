package com.github.patbattb.hw2.domain;

/**
 * An inheritor of {@link Task}.
 * A part of {@link EpicTask}.
 */
public final class SubTask extends Task {
    private final EpicTask parentEpicTask;

    public SubTask(String title, String description, EpicTask parentEpicTask) {
        super(title, description);
        this.parentEpicTask = parentEpicTask;
    }

    public SubTask(int id, String title, String description, TaskStatus taskStatus, EpicTask parentEpicTask) {
        super(id, title, description, taskStatus);
        this.parentEpicTask = parentEpicTask;
    }

    public EpicTask getParentEpicTask() {
        return parentEpicTask;
    }

    @Override
    public String toString() {
        return "SubTask{ parentEpicTask.id=" + parentEpicTask.getId()
                + "} " + super.toString();
    }
}
