package com.github.patbattb.hw7.httpclient;

import com.github.patbattb.hw7.manager.taskmanager.SerializeContainer;
import com.github.patbattb.hw7.serializer.ObjectParser;

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
    private static final String KEY = "/key1";
    private static final String TOKEN_PARAM = "?API_TOKEN=";

    private final HttpClient httpClient;
    private final URI url;
    private String apiToken;


    public KVClient(String url) {
        this.httpClient = HttpClient.newHttpClient();
        this.url = URI.create(url);
    }

    public void register() throws IOException, InterruptedException {
        URI registerURI = URI.create(this.url.toString() + REGISTER_SUFFIX);
        HttpRequest request = HttpRequest.newBuilder(registerURI).GET().build();
        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        apiToken = response.body();
        System.out.println("API_TOKEN получен: " + apiToken);
    }

    public void save(SerializeContainer serializeContainer) throws IOException, InterruptedException {
        URI saveURI = URI.create(url + SAVE_SUFFIX + KEY + TOKEN_PARAM + apiToken);
        String json = ObjectParser.OBJECT_SERIALIZER.toJson(serializeContainer);
        HttpRequest request = HttpRequest.newBuilder(saveURI)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        System.out.println("Response code: " + response.statusCode());
    }

    public String load() throws IOException, InterruptedException {
        URI loadURI = URI.create(url + LOAD_SUFFIX + KEY + TOKEN_PARAM + apiToken);
        HttpRequest request = HttpRequest.newBuilder(loadURI)
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        return response.body();
    }

}
