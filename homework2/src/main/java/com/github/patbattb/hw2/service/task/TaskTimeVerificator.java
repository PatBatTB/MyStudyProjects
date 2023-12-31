package com.github.patbattb.hw2.service.task;

import com.github.patbattb.hw2.domain.task.Task;
import com.github.patbattb.hw2.service.manager.TaskManager;

import java.util.Objects;

public final class TaskTimeVerificator {

    private TaskTimeVerificator() {
    }

    public static boolean findTimeOverlaps(TaskManager manager, Task task) {
        var taskStart = task.getStartTime();
        if (Objects.isNull(taskStart) || manager.priorityHistory().isEmpty()) return false;
        var taskEnd = task.getEndTime();
        var isOverlap = false;

        for (Task taskFromManager : manager.priorityHistory()) {
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
