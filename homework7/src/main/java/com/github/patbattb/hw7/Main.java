package com.github.patbattb.hw7;

import com.github.patbattb.taskmanager.backend.domain.task.Task;
import com.github.patbattb.taskmanager.backend.service.manager.Managers;
import com.github.patbattb.taskmanager.backend.service.manager.TaskManager;

import java.io.IOException;

@SuppressWarnings("HideUtilityClassConstructor")
public class Main {
    public static void main(String[] args) throws IOException {
        TaskManager manager = Managers.getDefaultTaskManager();
        Task task = new Task("t", "d");
        manager.addTask(task);
        manager.getListOfAllTasks().forEach(System.out::println);
        com.example.kvserver.Main.main(null);
    }
}
