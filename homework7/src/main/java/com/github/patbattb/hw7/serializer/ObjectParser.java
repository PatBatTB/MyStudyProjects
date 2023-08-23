package com.github.patbattb.hw7.serializer;

import com.github.patbattb.taskmanager.backend.task.domain.EpicTask;
import com.github.patbattb.taskmanager.backend.task.domain.SubTask;
import com.github.patbattb.taskmanager.backend.task.domain.Task;
import com.google.gson.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ObjectParser {

    private static final Gson OBJECT_SERIALIZER = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, TypeAdapters.getLocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, TypeAdapters.getDurationAdapter())
            .create();

    private ObjectParser() {
    }

    public static Optional<Task> taskFromJson(String json) {
        Optional<String> taskType = getValueFromJson(json, "type");
        Map<String, Class<? extends Task>> classMap = new HashMap<>() {{
            put("TASK", Task.class);
            put("EPIC", EpicTask.class);
            put("SUBTASK", SubTask.class);
        }};
        if (taskType.isEmpty() || !classMap.containsKey(taskType.get())) return Optional.empty();
        return Optional.ofNullable(OBJECT_SERIALIZER.fromJson(json, classMap.get(taskType.get())));

    }

    public static Optional<String> getValueFromJson(String json, String field) {
        JsonElement jsonElem = JsonParser.parseString(json);
        if (jsonElem.getClass() == JsonObject.class) {
            JsonObject jsonObj = (JsonObject) jsonElem;
            String fieldValue = jsonObj.get(field).getAsString();
            return Optional.of(fieldValue);
        } else return Optional.empty();

    }

    public static String objectToJson(Object obj) {
        return OBJECT_SERIALIZER.toJson(obj);
    }

}
