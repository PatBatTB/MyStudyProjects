package com.github.patbattb.hw2.service;

import com.github.patbattb.hw2.domain.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;

@SuppressWarnings("VisibilityModifier")
@ExtendWith(MockitoExtension.class)
abstract class HistoryManagerTest<T extends HistoryManager> {

    protected T historyManager;

    protected Task task;
    protected Task task1;
    protected Task task2;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        task = new Task("t", "d");
        task1 = new Task("t1", "d1");
        task2 = new Task("t2", "d2");
    }

    @Test
    @DisplayName("Check adding a task to the empty history manager")
    void shouldTaskAddedToEmptyHistoryManager() {
        historyManager.add(task);
        assertThat(historyManager.getHistory().get(0)).isEqualTo(task);
    }

    @Test
    @DisplayName("Check adding a task to the history manager containing other tasks.")
    void shouldTaskAddedToHistoryManagerWithOtherTasks() {
        historyManager.add(task);
        historyManager.add(task1);
        assertThat(historyManager.getHistory().size()).isEqualTo(2);
        assertThat(historyManager.getHistory().get(0)).isEqualTo(task1);
    }

    @Test
    @DisplayName("Check re-adding the task to the history manager")
    void shouldTaskReplacesItselfWhenReaddedToHistoryManager() {
        historyManager.add(task);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task);
        assertThat(historyManager.getHistory().size()).isEqualTo(3);
        assertThat(historyManager.getHistory().get(0)).isEqualTo(task);
    }

    @Test
    @DisplayName("Check removing task from empty history manager.")
    void shouldResultOfRemoveWillBeIgnoredIfIdDoesntExistsInHistory() {
        historyManager.remove(1);
        assertThat(historyManager.getHistory().isEmpty()).isEqualTo(true);
    }

    @Test
    @DisplayName("Check removing a task from start of the history list.")
    void shouldTaskRemovesFromStartOfHistory() {
        List<Task> resultList = List.of(task1, task);
        historyManager.add(task);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(task2.getId());
        assertThatList(historyManager.getHistory()).isEqualTo(resultList);
    }

    @Test
    @DisplayName("Check removing a task from end of the history list.")
    void shouldTaskRemovesFromEndOfHistory() {
        List<Task> resultList = List.of(task2, task1);
        historyManager.add(task);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(task.getId());
        assertThatList(historyManager.getHistory()).isEqualTo(resultList);
    }

    @Test
    @DisplayName("Check removing a task from the middle of the history list.")
    void shouldTaskRemovesFromMiddleOfHistory() {
        List<Task> resultList = List.of(task2, task);
        historyManager.add(task);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(task1.getId());
        assertThatList(historyManager.getHistory()).isEqualTo(resultList);
    }
}
