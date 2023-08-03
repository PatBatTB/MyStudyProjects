package com.github.patbattb.hw2.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Implementation of {@link Manager} with backing to file.
 * The class inherited from {@link DefaultTaskManager} and contains additional methods
 * to back up the instance to file after each operation.
 * If backup is already exists it will be overwritten, else a new file will be created.
 */
public final class FileBackedTaskManager extends DefaultTaskManager {
    private static final String FILENAME = "DefaultBackupFile.csv";
    private static final String HEADER = "id,type,name,status,description,epic";
    private final Path backup;

    public FileBackedTaskManager() {
        this.backup = Path.of("homework2", "src", "main", "resources", FILENAME);
    }

    public void save() throws IOException {
        if (!Files.exists(backup)) Files.createFile(backup);
        //TODO
    }

    private void load() {
    }
}
