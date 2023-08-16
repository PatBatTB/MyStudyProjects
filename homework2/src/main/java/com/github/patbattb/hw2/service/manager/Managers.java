package com.github.patbattb.hw2.service.manager;

import com.github.patbattb.hw2.domain.task.Task;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Util class for create right implementation of {@link TaskManager}.
 */
public final class Managers {
    private Managers() {
    }

    /**
     * Getter for default task manager.
     *
     * @return Default implementation of {@link TaskManager}.
     */
    public static TaskManager getDefaultTaskManager() {
        return new DefaultTaskManager();
    }

    /**
     * Getter for task manager with back up function to the file.
     *
     * @return File backed implementation of {@link TaskManager}.
     */
    public static FileBackedTaskManager getFileBackedTaskManager() {
        return new FileBackedTaskManager();
    }

    public static FileBackedTaskManager getFileBackedTaskManager(Path path) {
        return new FileBackedTaskManager(path);
    }

    /**
     * Getter for default history manager.
     *
     * @return Default implementation of {@link HistoryManager}.
     */
    public static HistoryManager getDefaultHistory() {
        return new DefaultHistoryManager();
    }

    public static String toString(List<Task> historyList) {
        List<String> idList = new ArrayList<>();
        for (Task task : historyList) {
            idList.add(Integer.toString(task.getId()));
        }
        return String.join(",", idList);

    }
}
