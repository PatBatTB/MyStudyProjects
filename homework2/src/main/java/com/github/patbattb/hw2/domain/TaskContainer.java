package com.github.patbattb.hw2.domain;

import java.util.HashMap;

/**
 * Contains maps with tasks.
 * A key of map is an identifier of task.
 */
public final class TaskContainer {

    private final HashMap<Integer, Task> ordinaryTaskMap;
    private final HashMap<Integer, EpicTask> epicTaskMap;
    private final HashMap<Integer, SubTask> subTaskMap; //TODO нужен ли контейнер, если таски хранятся в эпиках?

    public TaskContainer() {
        ordinaryTaskMap = new HashMap<>();
        epicTaskMap = new HashMap<>();
        subTaskMap = new HashMap<>();
    }

    public HashMap<Integer, Task> getOrdinaryTaskMap() {
        return ordinaryTaskMap;
    }

    public HashMap<Integer, EpicTask> getEpicTaskMap() {
        return epicTaskMap;
    }

    public HashMap<Integer, SubTask> getSubTaskMap() {
        return subTaskMap;
    }
}
