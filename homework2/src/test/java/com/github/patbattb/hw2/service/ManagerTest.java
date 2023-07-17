package com.github.patbattb.hw2.service;

import com.github.patbattb.hw2.domain.task.EpicTask;
import com.github.patbattb.hw2.domain.task.SubTask;
import com.github.patbattb.hw2.domain.task.Task;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ManagerTest {

    private Manager manager;

    @BeforeEach
    void setUp() {
        manager = new Manager();
    }

    @Test
    @DisplayName("Test adding an ordinary task to the right map in the container")
    void shouldOrdinaryTaskAddInRightMap() {
        Task task = new Task("testTitle", "testDescription");
        manager.addTask(task);
        Assertions.assertThat(manager.getTaskContainer().getOrdinaryTaskMap().containsValue(task))
                .isEqualTo(true);
    }

    @Test
    @DisplayName("Test adding an epic task to the right map in the container.")
    void shouldEpicTaskAddInRightMap() {
        EpicTask task = new EpicTask("testTitle", "testDescription");
        manager.addTask(task);
        Assertions.assertThat(manager.getTaskContainer().getEpicTaskMap().containsValue(task))
                .isEqualTo(true);
    }

    @Test
    @DisplayName("Test adding a subtask to the right map in the container.")
    void shouldSubTaskAddInRightMap() {
        EpicTask epic = Mockito.mock(EpicTask.class);
        SubTask task = new SubTask("testTitle", "testDescription", epic);
        manager.addTask(task);
        Assertions.assertThat(manager.getTaskContainer().getSubTaskMap().containsValue(task))
                .isEqualTo(true);
    }
}
