package com.github.patbattb.taskmanager.backend.service.manager;

import com.github.patbattb.taskmanager.backend.domain.task.EpicTask;
import com.github.patbattb.taskmanager.backend.domain.task.SubTask;
import com.github.patbattb.taskmanager.backend.domain.task.Task;
import com.github.patbattb.taskmanager.backend.exception.ManagerSaveException;
import com.github.patbattb.taskmanager.backend.service.IdProvider;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@code Manager} with backing to file.
 * The class inherited from {@link DefaultTaskManager} and contains additional methods
 * to back up the instance to file after each operation.
 * If backup is already exists it will be overwritten, else a new file will be created.
 */
public final class FileBackedTaskManager extends DefaultTaskManager {
    private static final String FILENAME = "DefaultBackupFile.csv";
    private static final String HEADER = "id,type,name,status,description,epic";
    private final Path backup;
    private int maxId = 0;

    public FileBackedTaskManager() {
        super();
        backup = Path.of("src", "main", "resources", FILENAME);
        load();
        IdProvider.setStartId(maxId);
    }

    public FileBackedTaskManager(Path path) {
        super();
        backup = path;
        load();
        IdProvider.setStartId(maxId);
    }

    @Override
    public Task getTask(int id) {
        Task result = super.getTask(id);
        save();
        return result;
    }

    @Override
    public void removeTask(int id) {
        super.removeTask(id);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    /**
     * Backing up manager to file.
     * Creates path's directories and the backup file is they don't exist.
     * Does nothing if manager doesn't have tasks.
     */
    private void save() {
        if (getListOfAllTasks().isEmpty()) return;
        try {
            createFile();
            writeData();
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
    }

    private void createFile() throws IOException {
        if (!Files.exists(backup)) {
            Files.createDirectories(backup.getParent());
            Files.createFile(backup);
        }
    }

    private void writeData() throws IOException {
        var taskList = new ArrayList<String>();
        for (Task task : getListOfAllTasks()) {
            taskList.add(task.toString());
        }
        Files.write(backup, taskList, StandardCharsets.UTF_8);
        Files.writeString(backup, "\n", StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        Files.writeString(backup, Managers.toString(history()), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }

    private void load() {
        if (Files.exists(backup)) {
            List<String> lines;
            try {
                lines = Files.readAllLines(backup, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new ManagerSaveException();
            }
            List<List<Task>> listContainer = new ArrayList<>(List.of(
                    new ArrayList<>(), // ordinaryList
                    new ArrayList<>(), // epicList
                    new ArrayList<>())); //subList
            parsingTasks(listContainer, lines);
            loadTasks(listContainer);
            touchTasksForHistory(lines);
        }
    }

    private void parsingTasks(List<List<Task>> container, List<String> lines) {
        List<Task> ordinaryList = container.get(0);
        List<Task> epicList = container.get(1);
        List<Task> subList = container.get(2);
        for (String line : lines) {
            if (line.isBlank()) return;
            List<String> dataList = List.of(line.split(","));
            int taskId = Integer.parseInt(dataList.get(0));
            if (taskId > maxId) {
                maxId = taskId;
            }
            switch (dataList.get(1)) {
                case "TASK" -> ordinaryList.add(Task.fromString(dataList));
                case "EPIC" -> epicList.add(EpicTask.fromString(dataList));
                case "SUBTASK" -> {
                    int epicIndex = Integer.parseInt(dataList.get(7));
                    EpicTask epic = null;
                    for (Task localEpic : epicList) {
                        if (epicIndex == localEpic.getId()) {
                            epic = (EpicTask) localEpic;
                            break;
                        }
                    }
                    subList.add(SubTask.fromString(dataList, epic));
                }
            }
        }
    }

    private void loadTasks(List<List<Task>> container) {
        for (int i = 0; i < 3; i++) {
            for (Task task : container.get(i)) {
                addTask(task);
            }
        }
    }

    private void touchTasksForHistory(List<String> lines) {
        int indexHistoryLine = -1;
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).isBlank()) indexHistoryLine = i + 1;
        }
        if (indexHistoryLine != -1 && indexHistoryLine < lines.size()) {
            String[] taskIdArray = lines.get(indexHistoryLine).split(",");
            for (int index = taskIdArray.length; index > 0; index--) {
                getTask(Integer.parseInt(taskIdArray[index - 1]));
            }
        }
    }

}
