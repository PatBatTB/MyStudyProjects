package com.github.patbattb.taskmanager.backend.service.task;

import com.github.patbattb.taskmanager.backend.domain.task.Task;
import com.github.patbattb.taskmanager.backend.service.manager.TaskManager;

import java.util.Objects;

public final class TaskTimeVerificator {

    private TaskTimeVerificator() {
    }

    public static boolean findTimeOverlaps(TaskManager manager, Task task) {
        var taskStart = task.getStartTime();
        if (Objects.isNull(taskStart) || manager.getPrioritizedTasks().isEmpty()) return false;
        var taskEnd = task.getEndTime();
        var isOverlap = false;

        for (Task taskFromManager : manager.getPrioritizedTasks()) {
            if (task.getId() == taskFromManager.getId()) continue;
            var manStart = taskFromManager.getStartTime();
            var manEnd = taskFromManager.getEndTime();
            if (Objects.isNull(manStart) || taskEnd.isBefore(manStart)) break;
            else if (taskEnd.isBefore(manEnd)) {
                System.out.println("Overlaps with " + taskFromManager);
                isOverlap = true;
            } else if (taskStart.isBefore(manEnd)) {
                System.out.println("Overlaps with " + taskFromManager);
                isOverlap = true;
            }
        }
        return isOverlap;
    }
}
