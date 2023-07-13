package com.github.patbattb.hw2.service;

import com.github.patbattb.hw2.domain.Task;
import com.github.patbattb.hw2.domain.TaskStatus;

/**
 * Service for update tasks.
 */
public final class TaskUpdater {
    private TaskUpdater() {
    }


    public static Task updateStatus(Task task, TaskStatus status) {
        return new Task(task.getId(), task.getTitle(), task.getDescription(), status);
    }


}
