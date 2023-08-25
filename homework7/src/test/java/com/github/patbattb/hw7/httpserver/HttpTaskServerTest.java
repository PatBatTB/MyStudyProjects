package com.github.patbattb.hw7.httpserver;

import com.github.patbattb.hw7.serializer.ObjectParser;
import com.github.patbattb.taskmanager.backend.manager.taskmanager.TaskManager;
import com.github.patbattb.taskmanager.backend.task.domain.EpicTask;
import com.github.patbattb.taskmanager.backend.task.domain.SubTask;
import com.github.patbattb.taskmanager.backend.task.domain.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HttpTaskServerTest {

    private static final String URL = "http://localhost:7050";
    private static final int PORT = 7050;
    private static final int HTTP_OK_STATUS = 200;
    private static final int HTTP_BAD_REQUEST_STATUS = 400;
    private static final int HTTP_NOT_FOUND_STATUS = 404;
    private static final int HTTP_NOT_ALLOWED_STATUS = 405;

    private HttpClient httpClient;
    @Mock
    private TaskManager taskManager;
    private HttpTaskServer taskServer;

    @BeforeEach
    void setUp() throws IOException {
        taskServer = new HttpTaskServer(taskManager, PORT);
        httpClient = HttpClient.newHttpClient();
        taskServer.start();
    }

    @AfterEach
    void tearDown() {
        taskServer.stop();
    }

    private HttpResponse<String> getResponseGET(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    }

    private HttpResponse<String> getResponseDELETE(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).DELETE().build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    }

    private HttpResponse<String> getResponsePOST(String url, String body) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(body)).build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
    }


    @Test
    @DisplayName("endpoint /tasks GET - HTTP_NOT_FOUND")
    void shouldTasksGETEndpointReturnsNotFound() throws IOException, InterruptedException {
        List<Task> aList = Collections.emptyList();
        Mockito.doReturn(aList).when(taskManager).getListOfAllTasks();
        var response = getResponseGET(URL + "/tasks");
        Mockito.verify(taskManager).getListOfAllTasks();
        assertThat(response.statusCode()).isEqualTo(HTTP_NOT_FOUND_STATUS);
    }

    @Test
    @DisplayName("endpoint /tasks GET - HTTP_OK")
    void shouldTasksGETEndpointReturnListOfTasks() throws IOException, InterruptedException {
        List<Task> aList = new ArrayList<>(List.of(new Task("t", "d")));
        Mockito.doReturn(aList).when(taskManager).getListOfAllTasks();
        var response = getResponseGET(URL + "/tasks");
        Mockito.verify(taskManager).getListOfAllTasks();
        assertThat(response.statusCode()).isEqualTo(HTTP_OK_STATUS);
        assertThat(response.body().isBlank()).isEqualTo(false);
    }

    @Test
    @DisplayName("endpoint /tasks DELETE - HTTP_NOT_FOUND")
    void shouldTasksDELETEEndpointReturnsNotFound() throws IOException, InterruptedException {
        List<Task> aList = Collections.emptyList();
        Mockito.doReturn(aList).when(taskManager).getListOfAllTasks();
        var response = getResponseDELETE(URL + "/tasks");
        Mockito.verify(taskManager).getListOfAllTasks();
        assertThat(response.statusCode()).isEqualTo(HTTP_NOT_FOUND_STATUS);
    }

    @Test
    @DisplayName("endpoint /tasks DELETE - HTTP_OK")
    void shouldTasksDELETEEndpointReturnsOK() throws IOException, InterruptedException {
        List<Task> aList = new ArrayList<>(List.of(new Task("t", "d")));
        Mockito.doReturn(aList).when(taskManager).getListOfAllTasks();
        var response = getResponseDELETE(URL + "/tasks");
        Mockito.verify(taskManager).getListOfAllTasks();
        Mockito.verify(taskManager).removeAllTasks();
        assertThat(response.statusCode()).isEqualTo(HTTP_OK_STATUS);
    }

    @Test
    @DisplayName("endpoint /task GET with param 'id' - HTTP_NOT_FOUND")
    void shouldTaskWithIdGETEndpointReturnsNotFound() throws IOException, InterruptedException {
        Mockito.doReturn(null).when(taskManager).getTask(1);
        var response = getResponseGET(URL + "/tasks/task?id=1");
        Mockito.verify(taskManager).getTask(1);
        assertThat(response.statusCode()).isEqualTo(HTTP_NOT_FOUND_STATUS);
    }

    @Test
    @DisplayName("endpoint /task GET with param 'id' - HTTP_OK")
    void shouldTaskWithIdGETEndpointReturnsOK() throws IOException, InterruptedException {
        var task = new Task("s", "d");
        Mockito.doReturn(task).when(taskManager).getTask(1);
        var response = getResponseGET(URL + "/tasks/task?id=1");
        Mockito.verify(taskManager).getTask(1);
        assertThat(response.statusCode()).isEqualTo(HTTP_OK_STATUS);
        assertThat(response.body().isBlank()).isEqualTo(false);
    }

    @Test
    @DisplayName("endpoint /task GET without param 'id' - HTTP_NOT_FOUND")
    void shouldTaskWithoutIdGETEndpointReturnsNotFound() throws IOException, InterruptedException {
        List<Task> list = Collections.emptyList();
        Mockito.doReturn(list).when(taskManager).getListOfOrdinaryTasks();
        var response = getResponseGET(URL + "/tasks/task");
        Mockito.verify(taskManager).getListOfOrdinaryTasks();
        assertThat(response.statusCode()).isEqualTo(HTTP_NOT_FOUND_STATUS);
    }

    @Test
    @DisplayName("endpoint /task GET without param 'id' - HTTP_OK ")
    void shouldTaskWithoutIdGETEndpointReturnsOK() throws IOException, InterruptedException {
        List<Task> list = List.of(new Task("t", "d"));
        Mockito.doReturn(list).when(taskManager).getListOfOrdinaryTasks();
        var response = getResponseGET(URL + "/tasks/task");
        Mockito.verify(taskManager).getListOfOrdinaryTasks();
        assertThat(response.statusCode()).isEqualTo(HTTP_OK_STATUS);
        assertThat(response.body().isBlank()).isEqualTo(false);
    }

    @Test
    @DisplayName("endpoint /task DELETE with param 'id' - HTTP_NOT_FOUND")
    void shouldTaskWithIdDELETEEndpointReturnsNotFound() throws IOException, InterruptedException {
        Mockito.doReturn(null).when(taskManager).removeTask(1);
        var response = getResponseDELETE(URL + "/tasks/task?id=1");
        Mockito.verify(taskManager).removeTask(1);
        assertThat(response.statusCode()).isEqualTo(HTTP_NOT_FOUND_STATUS);
    }

    @Test
    @DisplayName("endpoint /task DELETE with param 'id' - HTTP_OK")
    void shouldTaskWithIdDELETEEndpointReturnsOK() throws IOException, InterruptedException {
        var task = new Task("s", "d");
        Mockito.doReturn(task).when(taskManager).removeTask(1);
        var response = getResponseDELETE(URL + "/tasks/task?id=1");
        Mockito.verify(taskManager).removeTask(1);
        assertThat(response.statusCode()).isEqualTo(HTTP_OK_STATUS);
    }

    @Test
    @DisplayName("endpoint /task DELETE without param 'id' - HTTP_BAD_REQUEST")
    void shouldTaskWithIncorrectParamDELETEEndpointReturnsBadRequest() throws IOException, InterruptedException {
        var response = getResponseDELETE(URL + "/tasks/task?badquery=1");
        Mockito.verify(taskManager, Mockito.never()).removeTask(1);
        assertThat(response.statusCode()).isEqualTo(HTTP_BAD_REQUEST_STATUS);
    }

    @Test
    @DisplayName("endpoint /task POST with already contained task")
    void shouldTaskPOSTEndpointRequestUpdateMethodIfTaskIsExist() throws IOException, InterruptedException {
        var task = new Task("s", "d");
        List<Task> list = new ArrayList<>(List.of(task));
        Mockito.doReturn(list).when(taskManager).getListOfAllTasks();
        String serializeTask = ObjectParser.objectToJson(task);
        var response = getResponsePOST(URL + "/tasks/task", serializeTask);
        Mockito.verify(taskManager).getListOfAllTasks();
        Mockito.verify(taskManager).updateTask(task);
        assertThat(response.statusCode()).isEqualTo(HTTP_OK_STATUS);
    }

    @Test
    @DisplayName("endpoint /task POST with new task")
    void shouldTaskPOSTEndpointRequestAddMethodIfTaskIsNew() throws IOException, InterruptedException {
        var task = new Task("t", "d");
        List<Task> list = Collections.emptyList();
        Mockito.doReturn(list).when(taskManager).getListOfAllTasks();
        String serializeTask = ObjectParser.objectToJson(task);
        var response = getResponsePOST(URL + "/tasks/task", serializeTask);
        Mockito.verify(taskManager).getListOfAllTasks();
        Mockito.verify(taskManager).addTask(task);
        assertThat(response.statusCode()).isEqualTo(HTTP_OK_STATUS);
    }

    @Test
    @DisplayName("endpoint /subtasks GET - HTTP_OK")
    void shouldSubtasksGETEndpointReturnsOK() throws IOException, InterruptedException {
        var epic = new EpicTask("et", "ed");
        var sub = new SubTask("t", "d", 1);
        epic.getSubtaskIdList().add(2);
        List<SubTask> list = new ArrayList<>(List.of(new SubTask("t", "d", 1)));
        Mockito.doReturn(list).when(taskManager).getListOfSubTasks(epic);
        String serializeEpic = ObjectParser.objectToJson(epic);
        HttpRequest request = HttpRequest.newBuilder(URI.create(URL + "/subtasks/epic"))
                .method("GET", HttpRequest.BodyPublishers.ofString(serializeEpic)).build();
        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        Mockito.verify(taskManager).getListOfSubTasks(epic);
        assertThat(response.statusCode()).isEqualTo(HTTP_OK_STATUS);
    }

    @Test
    @DisplayName("endpoint /subtasks GET - HTTP_NOT_FOUND")
    void shouldSubtasksGETEndpointReturnsNotFound() throws IOException, InterruptedException {
        var epic = new EpicTask("et", "ed");
        HttpRequest request = HttpRequest.newBuilder(URI.create(URL + "/subtasks/epic"))
                .method("GET", HttpRequest.BodyPublishers.ofString("")).build();
        HttpResponse<String> response = httpClient.send(
                request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        Mockito.verify(taskManager, Mockito.never()).getListOfSubTasks(epic);
        assertThat(response.statusCode()).isEqualTo(HTTP_NOT_FOUND_STATUS);
    }

    @Test
    @DisplayName("endpoint /epics GET - HTTP_OK")
    void shouldEpicsGETEndpointReturnsOK() throws IOException, InterruptedException {
        var epic = new EpicTask("et", "ed");
        List<EpicTask> list = List.of(epic);
        Mockito.doReturn(list).when(taskManager).getListOfEpicTasks();
        var response = getResponseGET(URL + "/epics");
        Mockito.verify(taskManager).getListOfEpicTasks();
        assertThat(response.statusCode()).isEqualTo(HTTP_OK_STATUS);
        assertThat(response.body().isBlank()).isEqualTo(false);
    }

    @Test
    @DisplayName("endpoint /epics GET - HTTP_NOT_FOUND")
    void shouldEpicsGETEndpointReturnsNotFound() throws IOException, InterruptedException {
        Mockito.doReturn(Collections.emptyList()).when(taskManager).getListOfEpicTasks();
        var response = getResponseGET(URL + "/epics");
        Mockito.verify(taskManager).getListOfEpicTasks();
        assertThat(response.statusCode()).isEqualTo(HTTP_NOT_FOUND_STATUS);
    }

    @Test
    @DisplayName("endpoint /history GET - HTTP_OK")
    void shouldHistoryGETEndpointReturnsOK() throws IOException, InterruptedException {
        List<Task> list = List.of(new Task("t", "d"));
        Mockito.doReturn(list).when(taskManager).history();
        var response = getResponseGET(URL + "/tasks/history");
        Mockito.verify(taskManager).history();
        assertThat(response.statusCode()).isEqualTo(HTTP_OK_STATUS);
        assertThat(response.body().isBlank()).isEqualTo(false);
    }

    @Test
    @DisplayName("endpoint /history GET - HTTP_NOT_FOUND")
    void shouldHistoryGETEndpointReturnsNotFound() throws IOException, InterruptedException {
        Mockito.doReturn(Collections.emptyList()).when(taskManager).history();
        var response = getResponseGET(URL + "/tasks/history");
        Mockito.verify(taskManager).history();
        assertThat(response.statusCode()).isEqualTo(HTTP_NOT_FOUND_STATUS);
    }

    @Test
    @DisplayName("endpoint /priority GET - HTTP_OK")
    void shouldPriorityGETEndpointReturnsOK() throws IOException, InterruptedException {
        List<Task> list = List.of(new Task("t", "d"));
        Mockito.doReturn(list).when(taskManager).getPrioritizedTasks();
        var response = getResponseGET(URL + "/tasks/priority");
        Mockito.verify(taskManager).getPrioritizedTasks();
        assertThat(response.statusCode()).isEqualTo(HTTP_OK_STATUS);
        assertThat(response.body().isBlank()).isEqualTo(false);
    }

    @Test
    @DisplayName("endpoint /priority GET - HTTP_NOT_FOUND")
    void shouldPriorityGETEndpointReturnsNotFound() throws IOException, InterruptedException {
        Mockito.doReturn(Collections.emptyList()).when(taskManager).getPrioritizedTasks();
        var response = getResponseGET(URL + "/tasks/priority");
        Mockito.verify(taskManager).getPrioritizedTasks();
        assertThat(response.statusCode()).isEqualTo(HTTP_NOT_FOUND_STATUS);
    }


}
