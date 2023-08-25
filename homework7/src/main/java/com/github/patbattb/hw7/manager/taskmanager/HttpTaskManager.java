package com.github.patbattb.hw7.manager.taskmanager;


import com.github.patbattb.hw7.httpclient.KVClient;
import com.github.patbattb.hw7.serializer.ObjectParser;
import com.github.patbattb.taskmanager.backend.manager.taskmanager.FileBackedTaskManager;
import com.github.patbattb.taskmanager.backend.task.IdProvider;
import com.github.patbattb.taskmanager.backend.task.domain.Task;

import java.io.IOException;
import java.util.Comparator;

public final class HttpTaskManager extends FileBackedTaskManager {

    private final KVClient kvClient;
    private final SerializeContainer serializeContainer;

    public HttpTaskManager(String kvServerURI) throws IOException, InterruptedException {
        super();
        this.kvClient = new KVClient(kvServerURI);
        this.kvClient.register();
        this.serializeContainer = new SerializeContainer();
        load();
    }

    @Override
    protected void save() {
        try {
            serializeContainer.setTaskList(this.getListOfAllTasks());
            serializeContainer.setHistoryList(this.history());
            kvClient.save(serializeContainer);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void load() {
        if (kvClient == null) return;
        try {
            String json = kvClient.load();
            var tempSerializeContainer = ObjectParser.OBJECT_SERIALIZER.fromJson(json, SerializeContainer.class);
            if (tempSerializeContainer == null) return;
            tempSerializeContainer.getTaskList().forEach(this::addTask);
            tempSerializeContainer.getHistoryList().forEach(task -> this.getTask(task.getId()));
            int maxId = tempSerializeContainer.getTaskList().stream()
                    .map(Task::getId)
                    .max(Comparator.naturalOrder())
                    .orElse(0);
            IdProvider.setStartId(maxId);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
