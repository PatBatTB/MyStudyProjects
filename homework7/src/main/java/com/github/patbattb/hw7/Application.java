package com.github.patbattb.hw7;

import com.github.patbattb.hw7.httpserver.HttpTaskServer;
import com.github.patbattb.taskmanager.backend.domain.task.EpicTask;
import com.github.patbattb.taskmanager.backend.domain.task.SubTask;
import com.github.patbattb.taskmanager.backend.domain.task.Task;
import com.github.patbattb.taskmanager.backend.service.manager.Managers;
import com.github.patbattb.taskmanager.backend.service.manager.TaskManager;

import java.io.IOException;

@SuppressWarnings("HideUtilityClassConstructor")
public class Application {
    public static void main(String[] args) throws IOException, InterruptedException {
        TaskManager manager = Managers.getDefaultTaskManager();
        Task task = new Task("t", "d");
        EpicTask epic = new EpicTask("et", "ed");
        SubTask sub = new SubTask("st", "sd", epic);
        SubTask sub1 = new SubTask("st1", "sd1", epic);
        manager.addTask(task, epic, sub, sub1);
        manager.getListOfAllTasks().forEach(System.out::println);
        HttpTaskServer httpTaskServer = new HttpTaskServer(manager);
        httpTaskServer.start();
    }
}
