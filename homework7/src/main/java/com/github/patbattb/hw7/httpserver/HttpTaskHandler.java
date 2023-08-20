package com.github.patbattb.hw7.httpserver;

import com.github.patbattb.taskmanager.backend.domain.task.Task;
import com.github.patbattb.taskmanager.backend.service.manager.TaskManager;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class HttpTaskHandler {

    private static final String GET_METHOD = "GET";
    private static final String POST_METHOD = "POST";
    private static final String DELETE_METHOD = "DELETE";
    private static final String ID = "id";
    private static final int HTTP_OK_STATUS = 200;
    private static final int HTTP_BAD_REQUEST_STATUS = 400;
    private static final int HTTP_NOT_ALLOWED_STATUS = 405;
    private final TaskManager manager;

    HttpTaskHandler(TaskManager manager) {
        this.manager = manager;
    }

    public void tasks(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case GET_METHOD -> {
                //getListOfAllTasks()
                exchange.sendResponseHeaders(HTTP_OK_STATUS, 0);
                bodyString(exchange, "getListOfAllTasks()");

            }
            case DELETE_METHOD -> {
                //void removeAllTasks();
                exchange.sendResponseHeaders(HTTP_OK_STATUS, 0);
                bodyString(exchange, "removeAllTasks()");
            }
            default -> methodNotAllowed(exchange);
        }
    }

    public void task(HttpExchange exchange) throws IOException {
        switch (exchange.getRequestMethod()) {
            case GET_METHOD -> {
                //getTask(int id);
                //getListOfOrdinaryTasks();
                getTask(exchange);
            }
            case DELETE_METHOD -> {
                //removeTask(int id);
                Map<String, String> queries = parseQuery(exchange.getRequestURI().getRawQuery());
                if (queries.containsKey(ID)) {
                    exchange.sendResponseHeaders(HTTP_OK_STATUS, 0);
                    bodyString(exchange, String.format("removeTask(%s);", queries.get(ID)));
                } else {
                    exchange.sendResponseHeaders(HTTP_BAD_REQUEST_STATUS, 0);
                }
            }
            case POST_METHOD -> {
                //addTask(Task task);
                //updateTask(Task task);
                String strId = new String(exchange.getRequestBody().readAllBytes());
                Task task = strId.isBlank() ? null : manager.getTask(Integer.parseInt(strId));
                if (manager.getListOfAllTasks().contains(task)) {
                    exchange.sendResponseHeaders(HTTP_OK_STATUS, 0);
                    bodyString(exchange, "updateTask(Task task);");
                } else {
                    exchange.sendResponseHeaders(HTTP_OK_STATUS, 0);
                    bodyString(exchange, "addTask(Task task);");
                }
            }
            default -> methodNotAllowed(exchange);
        }
    }

    private void getTask(HttpExchange exchange) throws IOException {
        Map<String, String> queries = parseQuery(exchange.getRequestURI().getRawQuery());
        if (queries.containsKey(ID)) {
            getTask(exchange, queries.get(ID));
        } else {
            exchange.sendResponseHeaders(HTTP_OK_STATUS, 0);
            bodyString(exchange, "getListOfOrdinaryTasks();");
        }
    }

    private void getTask(HttpExchange exchange, String id) throws IOException {
        exchange.sendResponseHeaders(HTTP_OK_STATUS, 0);
        bodyString(exchange, String.format("getTask(%s);", id));
    }

    public void subtasks(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals(GET_METHOD)) {
            //getListOfSubTasks(EpicTask epic);
            exchange.sendResponseHeaders(HTTP_OK_STATUS, 0);
            bodyString(exchange, "getListOfSubTasks(EpicTask epic);");
        } else {
            methodNotAllowed(exchange);
        }
    }

    public void epics(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals(GET_METHOD)) {
            //getListOfEpicTasks();
            exchange.sendResponseHeaders(HTTP_OK_STATUS, 0);
            bodyString(exchange, "getListOfEpicTasks();");
        } else {
            methodNotAllowed(exchange);
        }
    }

    public void history(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals(GET_METHOD)) {
            //history();
            exchange.sendResponseHeaders(HTTP_OK_STATUS, 0);
            bodyString(exchange, "history();");
        } else {
            methodNotAllowed(exchange);
        }
    }

    public void priority(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().equals(GET_METHOD)) {
            //getPrioritizedTasks();
            exchange.sendResponseHeaders(HTTP_OK_STATUS, 0);
            bodyString(exchange, "getPrioritizedTasks();");
        } else {
            methodNotAllowed(exchange);
        }
    }

    private void bodyString(HttpExchange e, String s) throws IOException {
        try (OutputStream os = e.getResponseBody()) {
            os.write(s.getBytes(StandardCharsets.UTF_8));
        }
    }

    private Map<String, String> parseQuery(String rawQuery) {
        String regExp = "^.+?(?==)|(?<==).+$";
        Pattern pattern = Pattern.compile(regExp);
        List<String> aList = List.of(rawQuery.split("&"));
        Map<String, String> aMap = new HashMap<>(aList.size());
        for (String query : aList) {
            List<String> entry = new ArrayList<>();
            Matcher matcher = pattern.matcher(query);
            while (matcher.find()) {
                entry.add(matcher.group());
            }
            switch (entry.size()) {
                case 1 -> aMap.put(entry.get(0), null);
                case 2 -> aMap.put(entry.get(0), entry.get(1));
            }
        }
        return aMap;
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
