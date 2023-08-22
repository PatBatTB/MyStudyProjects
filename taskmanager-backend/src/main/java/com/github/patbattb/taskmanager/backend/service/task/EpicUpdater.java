package com.github.patbattb.taskmanager.backend.service.task;

import com.github.patbattb.taskmanager.backend.domain.TaskStatus;
import com.github.patbattb.taskmanager.backend.domain.task.EpicTask;
import com.github.patbattb.taskmanager.backend.domain.task.SubTask;
import com.github.patbattb.taskmanager.backend.domain.task.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public final class EpicUpdater {

    private static Map<Integer, SubTask> localMap;

    private EpicUpdater() {
    }

    /**
     * Updates {@link EpicTask} status and time from contained subtasks.
     * If status of the all subtasks is NEW - status of the epic changes to NEW.
     * If status of the all subtasks is DONE - status of the epic changes to DONE.
     * In all other cases - status of the epic changes to IN_PROGRESS.
     *
     * @param epic       An epic that required a status update.
     * @param subTaskMap Map with subtasks from the TaskManager.
     * @return Optional contained new EpicTask, or null if the EpicTask didn't change.
     */
    public static Optional<EpicTask> update(EpicTask epic, Map<Integer, SubTask> subTaskMap) {
        localMap = subTaskMap;
        EpicTask updatedTask = updateStatus(epic);
        updatedTask = updateTime(updatedTask);
        if (epic.equals(updatedTask)) return Optional.empty();
        return Optional.of(updatedTask);
    }

    private static EpicTask updateStatus(EpicTask epic) {
        if (epic.getSubtaskIdList().isEmpty()) {
            if (epic.getTaskStatus() == TaskStatus.NEW) {
                return epic;
            } else {
                return new EpicTask.Updater(epic).setTaskStatus(TaskStatus.NEW).update();
            }
        }
        List<SubTask> subTaskList = localMap.values().stream()
                .filter(st -> epic.getSubtaskIdList().contains(st.getId()))
                .toList();
        TaskStatus newStatus;
        if (subTaskList.stream().allMatch(st -> st.getTaskStatus() == TaskStatus.NEW)) {
            newStatus = TaskStatus.NEW;
        } else if (subTaskList.stream().allMatch(st -> st.getTaskStatus() == TaskStatus.DONE)) {
            newStatus = TaskStatus.DONE;
        } else {
            newStatus = TaskStatus.IN_PROGRESS;
        }
        return epic.getTaskStatus() == newStatus ? epic :
                new EpicTask.Updater(epic).setTaskStatus(newStatus).update();
    }

    private static EpicTask updateTime(EpicTask epic) {
        List<SubTask> subList = new ArrayList<>(localMap.values());
        LocalDateTime startTime = subList.stream()
                .map(Task::getStartTime)
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(null);
        LocalDateTime endTime = subList.stream()
                .map(Task::getEndTime)
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(null);
        if (Objects.equals(startTime, epic.getStartTime()) && Objects.equals(endTime, epic.getEndTime())) {
            return epic;
        }
        if (startTime == null || endTime == null) {
            return new EpicTask.Updater(epic).setStartTime(null).setDuration(null).update();
        }
        Duration duration = Duration.between(startTime, endTime);
        return new EpicTask.Updater(epic).setStartTime(startTime).setDuration(duration).update();
    }
}
