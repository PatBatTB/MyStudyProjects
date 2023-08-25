package com.github.patbattb.hw7.httpserver;

import com.github.patbattb.taskmanager.backend.manager.taskmanager.TaskManager;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public final class HttpTaskServer {

    public static final int DEFAULT_PORT = 8080;
    public static final String DEFAULT_HOSTNAME = "localhost";

    private final HttpServer httpServer;
    private final HttpTaskHandler taskHandler;

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this(taskManager, DEFAULT_HOSTNAME, DEFAULT_PORT);
    }

    public HttpTaskServer(TaskManager taskManager, int port) throws IOException {
        this(taskManager, DEFAULT_HOSTNAME, port);
    }

    public HttpTaskServer(TaskManager taskManager, String hostname) throws IOException {
        this(taskManager, hostname, DEFAULT_PORT);
    }

    public HttpTaskServer(TaskManager taskManager, String hostname, int port) throws IOException {
        this.taskHandler = new HttpTaskHandler(taskManager);
        this.httpServer = HttpServer.create(new InetSocketAddress(hostname, port), 0);
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

    public void stop() {
        httpServer.stop(0);
    }

}
