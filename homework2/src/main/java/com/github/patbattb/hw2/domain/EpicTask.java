package com.github.patbattb.hw2.domain;

import java.util.HashMap;

/**
 * An inheritor of {@link Task}.
 * A complex task than contains a list of {@link SubTask}.
 */
public final class EpicTask extends Task {
    private final HashMap<Integer, SubTask> subTasks;


    public EpicTask(String title, String description) {
        super(title, description);
        subTasks = new HashMap<>();
    }

    public EpicTask(int id, String title, String description,
                    TaskStatus taskStatus, HashMap<Integer, SubTask> subTasks) {
        super(id, title, description, taskStatus);
        this.subTasks = subTasks;
    }

    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public String toString() {
        return "EpicTask{ subTasks.size=" + subTasks.size()
                + "} " + super.toString();
    }
}
