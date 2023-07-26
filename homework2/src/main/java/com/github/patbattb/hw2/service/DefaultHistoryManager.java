package com.github.patbattb.hw2.service;

import com.github.patbattb.hw2.domain.task.Task;

import java.util.LinkedList;
import java.util.List;

public final class DefaultHistoryManager implements HistoryManager {

    private static final int NUMBERS_OF_ELEMENTS = 10;
    private final LinkedList<Task> tasks;

    public DefaultHistoryManager() {
        tasks = new LinkedList<>();
    }

    @Override
    public void add(Task task) {
        while (tasks.size() >= NUMBERS_OF_ELEMENTS) {
            tasks.removeFirst();
        }
        tasks.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return tasks;
    }
}
