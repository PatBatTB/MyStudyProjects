package com.github.patbattb.hw7.serializer;

import com.github.patbattb.hw7.manager.taskmanager.HttpTaskManager;
import com.github.patbattb.hw7.manager.taskmanager.SerializeContainer;
import com.github.patbattb.taskmanager.backend.task.domain.Task;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TypeAdapters {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss.n");

    private TypeAdapters() {
    }

    public static TypeAdapter<LocalDateTime> getLocalDateTimeAdapter() {
        return new TypeAdapter<>() {
            @Override
            public void write(JsonWriter out, LocalDateTime value) throws IOException {
                if (value == null) out.value("null");
                else out.value(value.format(DATE_TIME_FORMATTER));
            }

            @Override
            public LocalDateTime read(JsonReader in) throws IOException {
                String jsonString = in.nextString();
                return "null".equals(jsonString) ? null : LocalDateTime.parse(jsonString, DATE_TIME_FORMATTER);
            }
        };
    }

    public static TypeAdapter<Duration> getDurationAdapter() {
        return new TypeAdapter<>() {
            @Override
            public void write(JsonWriter out, Duration value) throws IOException {
                if (value == null) out.value("null");
                else out.value(String.valueOf(value.toNanos()));
            }

            @Override
            public Duration read(JsonReader in) throws IOException {
                String jsonString = in.nextString();
                return "null".equals(jsonString) ? null : Duration.ofNanos(Long.parseLong(jsonString));
            }
        };
    }

    public static TypeAdapter<HttpTaskManager> getTaskManagerTypeAdapter() {
        return new TypeAdapter<>() {
            @Override
            public void write(JsonWriter out, HttpTaskManager value) throws IOException {
                String taskList = ObjectParser.taskListToJson(value.getListOfAllTasks());
                String historyList = ObjectParser.taskListToJson(value.history());
                out.value(taskList).value(historyList);
            }

            @Override
            public HttpTaskManager read(JsonReader in) throws IOException {
                List<Task> taskList = ObjectParser.taskListFromJson(in.nextString());
                List<Task> historyList = new ArrayList<>(ObjectParser.taskListFromJson(in.nextString()));
                Collections.reverse(historyList);
                HttpTaskManager manager;
                try {
                    manager = new HttpTaskManager("");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                taskList.forEach(manager::addTask);
                HttpTaskManager finalManager = manager;
                historyList.forEach(task -> finalManager.getTask(task.getId()));

                return finalManager;
            }
        };
    }

    public static TypeAdapter<SerializeContainer> getSerializeContainerTypeAdapter() {
        return new TypeAdapter<>() {
            @Override
            public void write(JsonWriter out, SerializeContainer value) throws IOException {
                out.value(ObjectParser.taskListToJson(value.getTaskList()))
                        .value(ObjectParser.taskListToJson(value.getHistoryList()));
            }

            @Override
            public SerializeContainer read(JsonReader in) throws IOException {
                SerializeContainer serializeContainer = new SerializeContainer();
                serializeContainer.setTaskList(ObjectParser.taskListFromJson(in.nextString()));
                List<Task> historyList = new ArrayList<>(ObjectParser.taskListFromJson(in.nextString()));
                Collections.reverse(historyList);
                serializeContainer.setHistoryList(historyList);
                return serializeContainer;
            }
        };
    }
}
