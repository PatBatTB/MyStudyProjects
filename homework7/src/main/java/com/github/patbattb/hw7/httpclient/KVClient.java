package com.github.patbattb.hw7.httpclient;

import com.github.patbattb.taskmanager.backend.service.manager.TaskManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class KVClient {

    private static final String REGISTER_SUFFIX = "/register";
    private static final String SAVE_SUFFIX = "/save";
    private static final String LOAD_SUFFIX = "/load";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss.n");
    private static final TypeAdapter<LocalDateTime> LOCAL_DATE_TIME_TYPE_ADAPTER = new TypeAdapter<>() {
        @Override
        public void write(JsonWriter out, LocalDateTime value) throws IOException {
            if (value == null) {
                out.value("null");
            } else {
                out.value(value.format(DATE_TIME_FORMATTER));
            }
        }

        @Override
        public LocalDateTime read(JsonReader in) throws IOException {
            return in.nextString().equals("null") ? null : LocalDateTime.parse(in.nextString(), DATE_TIME_FORMATTER);
        }
    };
    private static final TypeAdapter<Duration> DURATION_TYPE_ADAPTER = new TypeAdapter<>() {
        @Override
        public void write(JsonWriter out, Duration value) throws IOException {
            if (value == null) {
                out.value("null");
            } else {
                out.value(String.valueOf(value.toNanos()));
            }
        }

        @Override
        public Duration read(JsonReader in) throws IOException {
            return in.nextString().equals("null") ? null : Duration.ofNanos(Long.parseLong(in.nextString()));
        }
    };

    private final HttpClient httpClient;
    private final URI url;
    private String apiToken;


    public KVClient(String url) {
        this.httpClient = HttpClient.newHttpClient();
        this.url = URI.create(url);
    }

    public void register() throws IOException, InterruptedException {
        URI registerUrl = URI.create(this.url.toString() + REGISTER_SUFFIX);
        HttpRequest request = HttpRequest.newBuilder(registerUrl).GET().build();
        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        apiToken = response.body();
        System.out.println("API_TOKEN получен: " + apiToken);
    }

    public void save(TaskManager manager) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, LOCAL_DATE_TIME_TYPE_ADAPTER)
                .registerTypeAdapter(Duration.class, DURATION_TYPE_ADAPTER)
                .create();

        System.out.println(gson.toJson(manager));

    }

    public void load() {

    }

}
