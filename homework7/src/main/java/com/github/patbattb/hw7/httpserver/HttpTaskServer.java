package com.github.patbattb.hw7.httpserver;

import com.github.patbattb.taskmanager.backend.service.manager.TaskManager;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public final class HttpTaskServer {

    public static final int PORT = 8080;

    private final HttpServer httpServer;
    private final HttpTaskHandler taskHandler;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.taskHandler = new HttpTaskHandler(taskManager);
        this.httpServer = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        httpServer.createContext("/tasks", taskHandler::tasks);
        httpServer.createContext("/tasks/task", taskHandler::task);
        httpServer.createContext("/tasks/history", taskHandler::history);
        httpServer.createContext("/tasks/priority", taskHandler::priority);
        httpServer.createContext("/subtasks/epic", taskHandler::subtasks);
        httpServer.createContext("/epics", taskHandler::epics);
    }

    public void start() {
        httpServer.start();
    }

}
