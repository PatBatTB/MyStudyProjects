package com.github.patbattb.hw7;

import com.github.patbattb.hw7.httpclient.KVClient;
import com.github.patbattb.hw7.httpserver.HttpTaskServer;
import com.github.patbattb.hw7.serializer.TypeAdapters;
import com.github.patbattb.taskmanager.backend.manager.Managers;
import com.github.patbattb.taskmanager.backend.manager.taskmanager.TaskManager;
import com.github.patbattb.taskmanager.backend.task.domain.EpicTask;
import com.github.patbattb.taskmanager.backend.task.domain.SubTask;
import com.github.patbattb.taskmanager.backend.task.domain.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

@SuppressWarnings("HideUtilityClassConstructor")
public class Application {

    private static final Gson OBJECT_SERIALIZER = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, TypeAdapters.getLocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, TypeAdapters.getDurationAdapter())
            .create();

    public static void main(String[] args) throws IOException {
        TaskManager manager = Managers.getDefaultTaskManager();
        Task task = new Task("t", "d", LocalDateTime.now(), Duration.ofDays(1));
        EpicTask epic = new EpicTask("et", "ed");
        SubTask sub = new SubTask("st", "sd", LocalDateTime.now().minusDays(1), Duration.ofDays(2), 2);
        SubTask sub1 = new SubTask("st1", "sd1", LocalDateTime.now().plusDays(1), Duration.ofDays(1), 2);
        manager.addTask(task, epic, sub, sub1);
        manager.getListOfAllTasks().forEach(System.out::println);
        HttpTaskServer httpTaskServer = new HttpTaskServer(manager);
        httpTaskServer.start();
        KVClient kvClient = new KVClient("http://localhost:8078");
        kvClient.save(manager);

    }
}
