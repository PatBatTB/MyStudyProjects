package com.github.patbattb.hw2.domain;

import java.util.ArrayList;

/**
 * An inheritor of {@link Task}.
 * A complex task than contains a list of {@link SubTask}.
 */
public final class EpicTask extends Task {
    private final ArrayList<SubTask> subTasks;


    public EpicTask(String title, String description) {
        super(title, description);
        subTasks = new ArrayList<>();
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
    }
}
