package com.github.patbattb.hw2.service;

import com.github.patbattb.hw2.domain.task.EpicTask;
import com.github.patbattb.hw2.domain.task.SubTask;
import com.github.patbattb.hw2.domain.task.Task;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public FileBackedTaskManager() {
        this.backup = Path.of("src", "main", "resources", FILENAME);
    }

    /**
     * Backing up manager to file.
     * Creates path's directories and the backup file is they don't exist.
     *
     * @throws IOException
     */
    public void save() throws IOException { //TODO make private
        createFile();
        writeData();
    }

    private void createFile() throws IOException {
        if (!Files.exists(backup)) {
            Files.createDirectories(backup.getParent());
            Files.createFile(backup);
        }
    }

    private void writeData() throws IOException {
        var aList = new ArrayList<String>();
        for (Task task : getListOfAllTasks()) {
            aList.add(task.toString());
        }

        Files.write(backup, aList, StandardCharsets.UTF_8);
    }

    public void load() throws IOException { //TODO make private
        List<String> lines = Files.readAllLines(backup, StandardCharsets.UTF_8);
        for (String line : lines) {
            List<String> dataList = List.of(line.split(","));
            switch (dataList.get(1)) {
                case "TASK" -> addTask(Task.fromString(dataList));
                case "EPIC" -> addTask(EpicTask.fromString(dataList));
                case "SUBTASK" -> addTask(SubTask.fromString(dataList));
            }
        }
    }


}
