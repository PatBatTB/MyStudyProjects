package com.github.patbattb.hw2.service;

import com.github.patbattb.hw2.domain.task.EpicTask;
import com.github.patbattb.hw2.domain.task.SubTask;
import com.github.patbattb.hw2.domain.task.Task;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DefaultManagerTest {

    private DefaultManager defaultManager;

    @BeforeEach
    void setUp() {
        defaultManager = new DefaultManager();
    }

    @Test
    @DisplayName("Test adding an ordinary task to the right map in the container")
    void shouldOrdinaryTaskAddInRightMap() {
        Task task = new Task("testTitle", "testDescription");
        defaultManager.addTask(task);
        Assertions.assertThat(defaultManager.getTaskContainer().getOrdinaryTaskMap().containsValue(task))
                .isEqualTo(true);
    }

    @Test
    @DisplayName("Test adding an epic task to the right map in the container.")
    void shouldEpicTaskAddInRightMap() {
        EpicTask task = new EpicTask("testTitle", "testDescription");
        defaultManager.addTask(task);
        Assertions.assertThat(defaultManager.getTaskContainer().getEpicTaskMap().containsValue(task))
                .isEqualTo(true);
    }

    @Test
    @DisplayName("Test adding a subtask to the right map in the container.")
    void shouldSubTaskAddInRightMap() {
        EpicTask epic = Mockito.mock(EpicTask.class);
        SubTask task = new SubTask("testTitle", "testDescription", epic);
        defaultManager.addTask(task);
        Assertions.assertThat(defaultManager.getTaskContainer().getSubTaskMap().containsValue(task))
                .isEqualTo(true);
    }
}
