package com.github.patbattb.hw2.service.task;

import com.github.patbattb.hw2.domain.TaskStatus;
import com.github.patbattb.hw2.domain.task.EpicTask;
import com.github.patbattb.hw2.domain.task.SubTask;
import com.github.patbattb.hw2.domain.task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public final class EpicUpdater {

    private EpicUpdater() {
    }

    public static EpicTask fullUpdate(EpicTask task) {
        task = updateStatus(task);
        task = updateTime(task);
        return task;
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

    public static EpicTask updateTime(EpicTask task) {
        List<SubTask> subTaskList = new ArrayList<>(task.getSubTasks().values());
        Optional<LocalDateTime> startTimeOptional = subTaskList.stream()
                .filter(t -> Objects.nonNull(t.getStartTime()))
                .map(Task::getStartTime)
                .min(Comparator.naturalOrder());
        LocalDateTime epicStartTime = startTimeOptional.orElse(null);
        if (epicStartTime == null) return new EpicTask.Updater(task)
                .setStartTime(null)
                .setDuration(null)
                .update();

        Optional<LocalDateTime> endTimeOptional = subTaskList.stream()
                .filter(t -> Objects.nonNull(t.getEndTime()))
                .map(Task::getEndTime)
                .max(Comparator.naturalOrder());
        LocalDateTime epicEndTime = endTimeOptional.orElse(null);
        Duration duration = Duration.between(epicStartTime, epicEndTime);
        return new EpicTask.Updater(task)
                .setStartTime(epicStartTime)
                .setDuration(duration)
                .update();
    }
}
