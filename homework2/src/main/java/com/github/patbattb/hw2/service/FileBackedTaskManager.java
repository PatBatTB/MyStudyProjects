package com.github.patbattb.hw2.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Implementation of {@link Manager} with backing to file.
 * The class inherited from {@link DefaultTaskManager} and contains additional methods
 * to backup the instance to file after each operation.
 * If backup is already exists it will be overwritten, else a new file will be created.
 * 
 */
public class FileBackedTaskManager extends DefaultTaskManager {
    private static final String FILENAME = "DefaultBackupFile.csv";
    private final Path backup;

    public FileBackedTaskManager() {
        this.backup = Path.of("resources", "backup", FILENAME);
    }
    
    
    public void save() {
        try {
            Files.createFile(backup);
        } catch (IOException ignored) {
        }
    }
    
    private void load() {
        
    }
    
}
