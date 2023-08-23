package com.github.patbattb.hw7.httpclient;

import com.github.patbattb.taskmanager.backend.manager.taskmanager.TaskManager;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public final class KVClient {

    private static final String REGISTER_SUFFIX = "/register";
    private static final String SAVE_SUFFIX = "/save";
    private static final String LOAD_SUFFIX = "/load";

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

    }

    public void load() {

    }

}
