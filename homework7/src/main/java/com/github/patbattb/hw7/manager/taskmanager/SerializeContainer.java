package com.github.patbattb.hw7.manager.taskmanager;

import com.github.patbattb.taskmanager.backend.task.domain.Task;

import java.util.ArrayList;
import java.util.List;

public final class SerializeContainer {
    private List<Task> taskList;
    private List<Task> historyList;

    public SerializeContainer() {
        this.taskList = new ArrayList<>();
        this.historyList = new ArrayList<>();
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<Task> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<Task> historyList) {
        this.historyList = historyList;
    }
}
