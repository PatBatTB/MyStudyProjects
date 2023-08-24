package com.github.patbattb.hw7.serializer;

import com.github.patbattb.taskmanager.backend.manager.taskmanager.DefaultTaskManager;
import com.github.patbattb.taskmanager.backend.task.domain.EpicTask;
import com.github.patbattb.taskmanager.backend.task.domain.SubTask;
import com.github.patbattb.taskmanager.backend.task.domain.Task;
import com.google.gson.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public final class ObjectParser {

    public static Optional<Task> taskFromJson(String json) {
        return taskFromJson(JsonParser.parseString(json));

    }

    public static Optional<Task> taskFromJson(JsonElement json) {
        Optional<String> taskType = getValueFromJson(json, "type");
        Map<String, Class<? extends Task>> classMap = new HashMap<>() {{
            put("TASK", Task.class);
            put("EPIC", EpicTask.class);
            put("SUBTASK", SubTask.class);
        }};
        if (taskType.isEmpty() || !classMap.containsKey(taskType.get())) return Optional.empty();
        return Optional.ofNullable(OBJECT_SERIALIZER.fromJson(json, classMap.get(taskType.get())));

    }    public static final Gson OBJECT_SERIALIZER = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, TypeAdapters.getLocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, TypeAdapters.getDurationAdapter())
            .registerTypeAdapter(DefaultTaskManager.class, TypeAdapters.getTaskManagerTypeAdapter())
            .create();

    private ObjectParser() {
    }

    public static Optional<String> getValueFromJson(JsonElement json, String field) {
        if (json.getClass() == JsonObject.class) {
            JsonObject jsonObj = (JsonObject) json;
            String fieldValue = jsonObj.get(field).getAsString();
            return Optional.of(fieldValue);
        } else return Optional.empty();

    }

    public static String taskListToJson(List<Task> taskList) {
        return OBJECT_SERIALIZER.toJson(taskList);
    }

    public static List<Task> taskListFromJson(String json) {
        JsonElement elem = JsonParser.parseString(json);
        if (elem instanceof JsonArray jsonArray) {
            return jsonArray.asList().stream()
                    .map(ObjectParser::taskFromJson)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        } else {
            return Collections.emptyList();
        }
    }





    public static String objectToJson(Object obj) {
        return OBJECT_SERIALIZER.toJson(obj);
    }

}
