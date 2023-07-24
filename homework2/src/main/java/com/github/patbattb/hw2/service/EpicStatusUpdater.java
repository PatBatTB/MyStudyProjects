package com.github.patbattb.hw2.service;

import com.github.patbattb.hw2.domain.TaskStatus;
import com.github.patbattb.hw2.domain.task.EpicTask;
import com.github.patbattb.hw2.domain.task.SubTask;

import java.util.HashMap;

public final class EpicStatusUpdater {

    private EpicStatusUpdater() {
    }

    public static EpicTask updateStatus(EpicTask task) {
        if (task.getSubTasks().isEmpty()) {
            if (task.getTaskStatus() == TaskStatus.NEW) {
                return task;
            } else {
                return new EpicTask.Updater(task).setTaskStatus(TaskStatus.NEW).update();
            }
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
            if (task.getTaskStatus() == TaskStatus.NEW) {
                return task;
            } else {
                return new EpicTask.Updater(task).setTaskStatus(TaskStatus.NEW).update();
            }
        }
        if (countNew == 0 && countProgress == 0) {
            if (task.getTaskStatus() == TaskStatus.DONE) {
                return task;
            } else {
                return new EpicTask.Updater(task).setTaskStatus(TaskStatus.DONE).update();
            }
        }
        if (task.getTaskStatus() == TaskStatus.IN_PROGRESS) {
            return task;
        } else {
            return new EpicTask.Updater(task).setTaskStatus(TaskStatus.IN_PROGRESS).update();
        }
    }
}
