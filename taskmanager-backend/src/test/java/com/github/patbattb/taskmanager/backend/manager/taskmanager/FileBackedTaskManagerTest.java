package com.github.patbattb.taskmanager.backend.manager.taskmanager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

class FileBackedTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {

    private Path pathToTestBackupFile;

    @BeforeEach
    void setUp() {
        super.setUp();
        pathToTestBackupFile = Path.of("src", "test", "resources", "DefaultBackup.csv");
        taskManager = new FileBackedTaskManager(pathToTestBackupFile);
    }

    @AfterEach
    void tearDown() throws IOException {
        if (Files.exists(pathToTestBackupFile)) {
            Files.delete(pathToTestBackupFile);
        }
    }

    private void save(FileBackedTaskManager taskManager) {
        Class<?> clazz = taskManager.getClass();
        try {
            Method method = clazz.getDeclaredMethod("save");
            method.setAccessible(true);
            method.invoke(taskManager);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Check save the manager without tasks to file.")
    void shouldDoesntCreateFileIfManagerNotHaveTasks() {
        var fileBackedTaskManager = new FileBackedTaskManager(pathToTestBackupFile);
        save(fileBackedTaskManager);
        Assertions.assertThatPath(pathToTestBackupFile).doesNotExist();
    }

    @Test
    @DisplayName("Check save the manager with the epic without subtasks to file.")
    void shouldCreateCorrectBackupFileWithHistoryAndEmptyEpicTask() {
        var fileBackedTaskManager = new FileBackedTaskManager(pathToTestBackupFile);
        String assertString = "3,EPIC,et1,NEW,ed1,null,null\n\n3";
        fileBackedTaskManager.addTask(emptyEpic);
        fileBackedTaskManager.getTask(emptyEpic.getId());
        Assertions.assertThatPath(pathToTestBackupFile).content(StandardCharsets.UTF_8).isEqualTo(assertString);
    }

    @Test
    @DisplayName("Check save the manager with tasks and without history")
    void shouldCreateCorrectBackupFileWithoutHistory() {
        var fileBackedTaskManager = new FileBackedTaskManager(pathToTestBackupFile);
        String assertString = "3,EPIC,et1,NEW,ed1,null,null\n\n";
        fileBackedTaskManager.addTask(emptyEpic);
        Assertions.assertThatPath(pathToTestBackupFile).content(StandardCharsets.UTF_8).isEqualTo(assertString);
    }

    @Test
    @DisplayName("Check load with the missing file.")
    void shouldCreateManagerWithoutTasksIfBackupFileDoesntExist() {
        var fileBackedTaskManager = new FileBackedTaskManager(pathToTestBackupFile);
        Assertions.assertThatList(fileBackedTaskManager.getListOfAllTasks()).isEmpty();
    }

    @Test
    @DisplayName("Check load from the file with the task and the history.")
    void shouldCreateManagerWithTaskAndHistoryFromBackupFile() throws IOException {
        String backupString = "3,EPIC,et1,NEW,ed1,null,null\n\n3";
        String assertString = "3,EPIC,et1,NEW,ed1,null,null";
        Files.createDirectories(pathToTestBackupFile.getParent());
        Files.createFile(pathToTestBackupFile);
        Files.writeString(pathToTestBackupFile, backupString, StandardCharsets.UTF_8);
        var fileBackedTaskManager = new FileBackedTaskManager(pathToTestBackupFile);
        Assertions.assertThat(fileBackedTaskManager.getTaskContainer()
                .getEpicTaskMap().get(3).toString()).isEqualTo(assertString);
        Assertions.assertThat(fileBackedTaskManager.history().get(0).toString()).isEqualTo(assertString);

    }

    @Test
    @DisplayName("Check load from file with task and without history")
    void shouldCreateManagerWithTaskAndWithoutHistoryFromBackupFile() throws IOException {
        String backupString = "3,EPIC,et1,NEW,ed1,null,null\n\n";
        String assertString = "3,EPIC,et1,NEW,ed1,null,null";
        Files.createDirectories(pathToTestBackupFile.getParent());
        Files.createFile(pathToTestBackupFile);
        Files.writeString(pathToTestBackupFile, backupString, StandardCharsets.UTF_8);
        var fileBackedTaskManager = new FileBackedTaskManager(pathToTestBackupFile);
        Assertions.assertThat(fileBackedTaskManager.getTaskContainer()
                .getEpicTaskMap().get(3).toString()).isEqualTo(assertString);
        Assertions.assertThatList(fileBackedTaskManager.history()).isEmpty();
    }

}
