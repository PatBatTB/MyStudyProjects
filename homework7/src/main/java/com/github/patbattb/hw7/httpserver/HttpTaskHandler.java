package com.github.patbattb.hw7.httpserver;

import com.github.patbattb.hw7.serializer.ObjectParser;
import com.github.patbattb.taskmanager.backend.manager.taskmanager.TaskManager;
import com.github.patbattb.taskmanager.backend.task.domain.EpicTask;
import com.github.patbattb.taskmanager.backend.task.domain.SubTask;
import com.github.patbattb.taskmanager.backend.task.domain.Task;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HttpTaskHandler {

    private static final String GET_METHOD = "GET";
    private static final String POST_METHOD = "POST";
    private static final String DELETE_METHOD = "DELETE";
    private static final int HTTP_OK_STATUS = 200;
    private static final int HTTP_BAD_REQUEST_STATUS = 400;
    private static final int HTTP_NOT_FOUND_STATUS = 404;
    private static final int HTTP_NOT_ALLOWED_STATUS = 405;

    private final TaskManager manager;

    HttpTaskHandler(TaskManager manager) {
        this.manager = manager;
    }

    public void tasks(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case GET_METHOD -> {
                List<Task> listOfAllTasks = manager.getListOfAllTasks();
                if (!listOfAllTasks.isEmpty()) {
                    exchange.sendResponseHeaders(HTTP_OK_STATUS, 0);
                    bodyString(exchange, ObjectParser.objectToJson(listOfAllTasks));
                } else {
                    exchange.sendResponseHeaders(HTTP_NOT_FOUND_STATUS, -1);
                }

            }
            case DELETE_METHOD -> {
                List<Task> listOfAllTasks = manager.getListOfAllTasks();
                if (!listOfAllTasks.isEmpty()) {
                    manager.removeAllTasks();
                    exchange.sendResponseHeaders(HTTP_OK_STATUS, -1);
                } else {
                    exchange.sendResponseHeaders(HTTP_NOT_FOUND_STATUS, -1);
                }
            }
            default -> methodNotAllowed(exchange);
        }
    }

    public void task(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case GET_METHOD -> {
                Optional<Integer> idOptional = parseIdFromQuery(exchange.getRequestURI().getRawQuery());
                if (idOptional.isPresent()) {
                    Optional<Task> taskOptional = Optional.ofNullable(manager.getTask(idOptional.get()));
                    if (taskOptional.isPresent()) {
                        exchange.sendResponseHeaders(HTTP_OK_STATUS, 0);
                        bodyString(exchange, ObjectParser.objectToJson(taskOptional.get()));
                    } else {
                        exchange.sendResponseHeaders(HTTP_NOT_FOUND_STATUS, -1);
                    }
                } else {
                    List<Task> listOfTasks = manager.getListOfOrdinaryTasks();
                    if (!listOfTasks.isEmpty()) {
                        exchange.sendResponseHeaders(HTTP_OK_STATUS, 0);
                        bodyString(exchange, ObjectParser.objectToJson(listOfTasks));
                    } else {
                        exchange.sendResponseHeaders(HTTP_NOT_FOUND_STATUS, -1);
                    }
                }
            }
            case DELETE_METHOD -> {
                Optional<Integer> idOptional = parseIdFromQuery(exchange.getRequestURI().getRawQuery());
                if (idOptional.isPresent()) {
                    Task task = manager.removeTask(idOptional.get());
                    if (task != null) {
                        exchange.sendResponseHeaders(HTTP_OK_STATUS, -1);
                    } else {
                        exchange.sendResponseHeaders(HTTP_NOT_FOUND_STATUS, -1);
                    }
                } else {
                    exchange.sendResponseHeaders(HTTP_BAD_REQUEST_STATUS, -1);
                }
            }
            case POST_METHOD -> {
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                Optional<Task> taskOptional = ObjectParser.taskFromJson(requestBody);
                if (taskOptional.isPresent()) {
                    if (manager.getListOfAllTasks().contains(taskOptional.get())) {
                        manager.updateTask(taskOptional.get());
                        exchange.sendResponseHeaders(HTTP_OK_STATUS, -1);
                    } else {
                        manager.addTask(taskOptional.get());
                        exchange.sendResponseHeaders(HTTP_OK_STATUS, -1);
                    }
                }
            }
            default -> methodNotAllowed(exchange);
        }
    }

    public void subtasks(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals(GET_METHOD)) {
            Optional<Task> taskOptional = ObjectParser
                    .taskFromJson(new String(exchange.getRequestBody().readAllBytes()));
            if (taskOptional.isPresent() && taskOptional.get() instanceof EpicTask epic) {
                List<SubTask> listOfSubTasks = manager.getListOfSubTasks(epic);
                exchange.sendResponseHeaders(HTTP_OK_STATUS, 0);
                bodyString(exchange, ObjectParser.objectToJson(listOfSubTasks));
            } else {
                exchange.sendResponseHeaders(HTTP_NOT_FOUND_STATUS, -1);
            }
        } else {
            methodNotAllowed(exchange);
        }
    }

    public void epics(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals(GET_METHOD)) {
            List<EpicTask> epicTaskList = manager.getListOfEpicTasks();
            if (!epicTaskList.isEmpty()) {
                exchange.sendResponseHeaders(HTTP_OK_STATUS, 0);
                bodyString(exchange, ObjectParser.objectToJson(epicTaskList));
            } else {
                exchange.sendResponseHeaders(HTTP_NOT_FOUND_STATUS, -1);
            }
        } else {
            methodNotAllowed(exchange);
        }
    }

    public void history(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals(GET_METHOD)) {
            List<Task> taskList = manager.history();
            if (!taskList.isEmpty()) {
                exchange.sendResponseHeaders(HTTP_OK_STATUS, 0);
                bodyString(exchange, ObjectParser.objectToJson(taskList));
            } else {
                exchange.sendResponseHeaders(HTTP_NOT_FOUND_STATUS, -1);
            }
        } else {
            methodNotAllowed(exchange);
        }
    }

    public void priority(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals(GET_METHOD)) {
            List<Task> taskList = manager.getPrioritizedTasks();
            if (!taskList.isEmpty()) {
                exchange.sendResponseHeaders(HTTP_OK_STATUS, 0);
                bodyString(exchange, ObjectParser.objectToJson(taskList));
            } else {
                exchange.sendResponseHeaders(HTTP_NOT_FOUND_STATUS, -1);
            }
        } else {
            methodNotAllowed(exchange);
        }
    }

    private void bodyString(HttpExchange e, String s) throws IOException {
        try (OutputStream os = e.getResponseBody()) {
            os.write(s.getBytes(StandardCharsets.UTF_8));
        }
    }

    private Optional<Integer> parseIdFromQuery(String rawQuery) {
        if (rawQuery != null) {
            String regExp = "(?<=id=)\\d*";
            Pattern pattern = Pattern.compile(regExp);
            Matcher matcher = pattern.matcher(rawQuery);
            if (matcher.find()) return Optional.of(Integer.parseInt(matcher.group()));
        }
        return Optional.empty();
    }

    private void methodNotAllowed(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(HTTP_NOT_ALLOWED_STATUS, 0);
        String body = String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                <title>405 Method Not Allowed</title>
                </head>
                <body>
                <h1>Method Not Allowed</h1>
                <p>The method %s is not allowed for the requested resource.</p>
                </body>
                </html>
                """, exchange.getRequestMethod());
        bodyString(exchange, body);
    }
}
