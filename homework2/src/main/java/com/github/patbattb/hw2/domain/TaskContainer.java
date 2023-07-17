package com.github.patbattb.hw2.domain;

import com.github.patbattb.hw2.domain.task.EpicTask;
import com.github.patbattb.hw2.domain.task.SubTask;
import com.github.patbattb.hw2.domain.task.Task;

import java.util.HashMap;

/**
 * Contains maps with tasks.
 * A key of map is an identifier of task.
 */
public final class TaskContainer {

    private final HashMap<Integer, Task> ordinaryTaskMap;
    private final HashMap<Integer, EpicTask> epicTaskMap;
    private final HashMap<Integer, SubTask> subTaskMap;

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
