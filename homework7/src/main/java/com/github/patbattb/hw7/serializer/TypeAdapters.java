package com.github.patbattb.hw7.serializer;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

}
