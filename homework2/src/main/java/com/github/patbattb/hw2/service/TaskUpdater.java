package com.github.patbattb.hw2.service;

import com.github.patbattb.hw2.domain.EpicTask;
import com.github.patbattb.hw2.domain.SubTask;
import com.github.patbattb.hw2.domain.Task;
import com.github.patbattb.hw2.domain.TaskStatus;

import java.util.HashMap;

/**
 * Service for update tasks.
 */
public final class TaskUpdater {
    private TaskUpdater() {
    }


    public static Task updateStatus(Task task, TaskStatus status) {
        return new Task(task.getId(), task.getTitle(), task.getDescription(), status);
    }

    public static SubTask updateStatus(SubTask task, TaskStatus status) {
        HashMap<Integer, SubTask> newMap = task.getParentEpicTask().getSubTasks();
        SubTask newTask = new SubTask(task.getId(), task.getTitle(),
                task.getDescription(), status, task.getParentEpicTask());
        newMap.put(newTask.getId(), newTask);
        EpicTask oldEpic = task.getParentEpicTask();
        EpicTask newEpic = new EpicTask(oldEpic.getId(), oldEpic.getTitle(), oldEpic.getDescription(),
                oldEpic.getTaskStatus(), newMap);
        EpicTask updateEpic = calculateEpicStatus(newEpic);
        return new SubTask(newTask.getId(), newTask.getTitle(),
                newTask.getDescription(), newTask.getTaskStatus(), updateEpic);

    }

    public static EpicTask calculateEpicStatus(EpicTask task) {
        if (task.getSubTasks().isEmpty()) {
            return task;
        }
        HashMap<TaskStatus, Integer> countMap = new HashMap<>();
        countMap.put(TaskStatus.NEW, 0);
        countMap.put(TaskStatus.IN_PROGRESS, 0);
        countMap.put(TaskStatus.DONE, 0);
        for (SubTask subTask : task.getSubTasks().values()) {
            TaskStatus taskStatus = subTask.getTaskStatus();
            countMap.put(taskStatus, countMap.get(taskStatus) + 1);
        }
        int countNew = countMap.get(TaskStatus.NEW);
        int countProgress = countMap.get(TaskStatus.IN_PROGRESS);
        int countDone = countMap.get(TaskStatus.DONE);
        if (countDone == 0 && countProgress == 0) {
            return new EpicTask(task.getId(), task.getTitle(),
                    task.getDescription(), TaskStatus.NEW, task.getSubTasks());
        }
        if (countNew == 0 && countProgress == 0) {
            return new EpicTask(task.getId(), task.getTitle(),
                    task.getDescription(), TaskStatus.DONE, task.getSubTasks());
        }
        return new EpicTask(task.getId(), task.getTitle(),
                task.getDescription(), TaskStatus.IN_PROGRESS, task.getSubTasks());

    }

}
