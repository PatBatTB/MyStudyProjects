package com.github.patbattb.taskmanager.backend.manager.historymanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

public final class DefaultHistoryManagerTest extends HistoryManagerTest<DefaultHistoryManager> {

    private int size;
    private Field field;

    @Override
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        super.setUp();
        historyManager = new DefaultHistoryManager();
        Class<?> clazz = historyManager.getClass();
        field = clazz.getDeclaredField("size");
        field.setAccessible(true);
    }

    @Test
    @DisplayName("Check the 'size' filed after adding task")
    void shouldSizeFieldIncreasesAfterAddingTask() throws IllegalAccessException {
        historyManager.add(task);
        size = (int) field.get(historyManager);
        assertThat(size).isEqualTo(1);
    }

    @Test
    @DisplayName("Check the 'size' field after re-adding task")
    void shouldSizeFieldDoesntChangeAfterReAddingExistingTask() throws IllegalAccessException {
        historyManager.add(task);
        historyManager.add(task);
        size = (int) field.get(historyManager);
        assertThat(size).isEqualTo(1);
    }

    @Test
    @DisplayName("Check the 'size' field after removing existing task.")
    void shouldSizeFieldDecreasesAfterRemovingTask() throws IllegalAccessException {
        historyManager.add(task);
        historyManager.remove(task.getId());
        size = (int) field.get(historyManager);
        assertThat(size).isEqualTo(0);
    }

    @Test
    @DisplayName("Check the 'size' field after removing if task doesn't exists.")
    void shouldSizeFiledDoesntChangeAfterRemovingMissingTask() throws IllegalAccessException {
        historyManager.add(task);
        historyManager.remove(task1.getId());
        size = (int) field.get(historyManager);
        assertThat(size).isEqualTo(1);
    }
}
