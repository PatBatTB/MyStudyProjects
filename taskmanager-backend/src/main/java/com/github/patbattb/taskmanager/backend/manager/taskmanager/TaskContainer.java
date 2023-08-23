package com.github.patbattb.taskmanager.backend.manager.taskmanager;

import com.github.patbattb.taskmanager.backend.task.domain.EpicTask;
import com.github.patbattb.taskmanager.backend.task.domain.SubTask;
import com.github.patbattb.taskmanager.backend.task.domain.Task;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Contains maps with tasks.
 * A key of map is an identifier of task.
 */
public final class TaskContainer {

    private final HashMap<Integer, Task> ordinaryTaskMap;
    private final HashMap<Integer, EpicTask> epicTaskMap;
    private final HashMap<Integer, SubTask> subTaskMap;
    private final ArrayList<HashMap<Integer, ? extends Task>> listOfAllTaskMaps;

    public TaskContainer() {
        ordinaryTaskMap = new HashMap<>();
        epicTaskMap = new HashMap<>();
        subTaskMap = new HashMap<>();
        listOfAllTaskMaps = new ArrayList<>();
        listOfAllTaskMaps.add(ordinaryTaskMap);
        listOfAllTaskMaps.add(epicTaskMap);
        listOfAllTaskMaps.add(subTaskMap);

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

    public ArrayList<HashMap<Integer, ? extends Task>> getListOfAllTaskMaps() {
        return listOfAllTaskMaps;
    }
}
