package com.github.patbattb.hw2.service;

import com.github.patbattb.taskmanager.backend.domain.task.EpicTask;
import com.github.patbattb.taskmanager.backend.domain.task.SubTask;
import com.github.patbattb.taskmanager.backend.domain.task.Task;
import com.github.patbattb.taskmanager.backend.service.manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;

@SuppressWarnings("VisibilityModifier")
abstract class TaskManagerTest<T extends TaskManager> {

    private final List<Task> emptyAssertList = Collections.emptyList();
    protected T taskManager;
    protected Task task;
    protected EpicTask epic;
    protected EpicTask emptyEpic;
    protected SubTask sub;

    @BeforeEach
    void setUp() {
        task = new Task("t", "d");
        epic = new EpicTask("et", "ed");
        emptyEpic = new EpicTask("et1", "ed1");
        sub = new SubTask("st", "sd", epic);
    }

    private void addTasksToManager(Task... tasks) {
        Arrays.stream(tasks).forEach(taskManager::addTask);
    }

    @Test
    @DisplayName("Check getting an empty list of all tasks.")
    void shouldGetEmptyListIfTasksHaveNotBeenAdded() {
        assertThatList(taskManager.getListOfAllTasks()).isEqualTo(emptyAssertList);
    }

    @Test
    @DisplayName("Check getting a list with tasks.")
    void shouldGetCorrectListWithAllTasks() {
        List<Task> assertList = List.of(task, epic, sub);
        addTasksToManager(task, epic, sub);
        assertThatList(taskManager.getListOfAllTasks()).isEqualTo(assertList);
    }

    @Test
    @DisplayName("Check getting an empty list of ordinary tasks.")
    void shouldGetEmptyListIfOrdinaryTasksHaveNotBeenAdded() {
        addTasksToManager(epic, sub);
        assertThatList(taskManager.getListOfOrdinaryTasks()).isEqualTo(emptyAssertList);
    }

    @Test
    @DisplayName("Check getting a list of ordinary tasks.")
    void shouldGetCorrectListOfOrdinaryTasks() {
        List<Task> assertList = List.of(task);
        taskManager.addTask(task);
        assertThatList(taskManager.getListOfOrdinaryTasks()).isEqualTo(assertList);
    }

    @Test
    @DisplayName("Check getting an empty list of epic tasks.")
    void shouldGetEmptyListIfEpicTasksHaveNotBeenAdded() {
        taskManager.addTask(task);
        assertThatList(taskManager.getListOfEpicTasks()).isEqualTo(emptyAssertList);
    }

    @Test
    @DisplayName("Check getting a list of epic tasks.")
    void shouldGetCorrectListOfEpicTasks() {
        List<Task> assertList = List.of(epic);
        taskManager.addTask(epic);
        assertThatList(taskManager.getListOfEpicTasks()).isEqualTo(assertList);
    }

    @Test
    @DisplayName("Check getting an empty list of subtasks.")
    void shouldGetEmptyListofSubTasksIfEpicDoesntHaveSubTasks() {
        taskManager.addTask(emptyEpic);
        assertThatList(taskManager.getListOfSubTasks(emptyEpic)).isEqualTo(emptyAssertList);
    }

    @Test
    @DisplayName("Check getting a list of subtasks.")
    void shouldGetCorrectListOfSubTasks() {
        List<Task> assertList = List.of(sub);
        taskManager.addTask(epic);
        taskManager.addTask(sub);
        assertThatList(taskManager.getListOfSubTasks(epic)).isEqualTo(assertList);
    }

    @Test
    @DisplayName("Check getting a task for incorrect ID")
    void shouldGetNullIfTaskDoesntExists() {
        assertThat(taskManager.getTask(task.getId())).isNull();
    }

    @Test
    @DisplayName("Check getting a task for ID")
    void shouldGetTaskForId() {
        addTasksToManager(task, epic, sub);
        assertThat(taskManager.getTask(task.getId())).isEqualTo(task);
        assertThat(taskManager.getTask(epic.getId())).isEqualTo(epic);
        assertThat(taskManager.getTask(sub.getId())).isEqualTo(sub);
    }

    @Test
    @DisplayName("Check removing a task for incorrect ID.")
    void shouldGetSameListsBeforeAndAfterRemoving() {
        List<Task> assertList = taskManager.getListOfAllTasks();
        taskManager.removeTask(task.getId());
        assertThatList(taskManager.getListOfAllTasks()).isEqualTo(assertList);
    }

    @Test
    @DisplayName("Check removing a task for ID.")
    void shouldListOfTasksNotHaveTaskAfterRemoving() {
        List<Task> assertList = List.of(task, emptyEpic);
        addTasksToManager(task, epic, emptyEpic, sub);
        taskManager.removeTask(epic.getId());
        assertThatList(taskManager.getListOfAllTasks()).isEqualTo(assertList);
    }

    @Test
    @DisplayName("Check removing all tasks.")
    void shouldManagerNotHaveTasksAfterRemovingAll() {
        addTasksToManager(task, epic, sub);
        taskManager.removeAllTasks();
        assertThatList(taskManager.getListOfAllTasks()).isEqualTo(emptyAssertList);
    }

    @Test
    @DisplayName("Check adding a task to the right map.")
    void shouldTaskAddToRightMapInContainer() {
        taskManager.addTask(task);
        assertThat(taskManager.getListOfOrdinaryTasks()).contains(task);
        taskManager.addTask(epic);
        assertThat(taskManager.getListOfEpicTasks()).contains(epic);
        taskManager.addTask(sub);
        assertThat(taskManager.getListOfSubTasks(epic)).contains(sub);
    }

    @Test
    @DisplayName("Check updating existed task.")
    void shouldContainerContainsNewInstanceOfTaskAfterUpdating() {
        taskManager.addTask(task);
        Task updatedTask = new Task.Updater(task).update();
        taskManager.updateTask(updatedTask);
        assertThat(taskManager.getListOfOrdinaryTasks()).contains(updatedTask);

    }

    @Test
    @DisplayName("Check getting a list of history.")
    void shouldGetListOfGettingTasks() {
        List<Task> assertList = List.of(sub, task, epic);
        addTasksToManager(task, epic, sub);
        taskManager.getTask(epic.getId());
        taskManager.getTask(task.getId());
        taskManager.getTask(sub.getId());
        assertThatList(taskManager.history()).isEqualTo(assertList);
    }
}
