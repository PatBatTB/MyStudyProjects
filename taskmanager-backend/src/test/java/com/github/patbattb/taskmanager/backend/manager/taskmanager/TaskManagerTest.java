package com.github.patbattb.taskmanager.backend.manager.taskmanager;

import com.github.patbattb.taskmanager.backend.task.TaskStatus;
import com.github.patbattb.taskmanager.backend.task.domain.EpicTask;
import com.github.patbattb.taskmanager.backend.task.domain.SubTask;
import com.github.patbattb.taskmanager.backend.task.domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatList;

@SuppressWarnings("VisibilityModifier")
abstract class TaskManagerTest<T extends TaskManager> {

    protected final LocalDateTime start = LocalDateTime.of(2023, Month.AUGUST, 10, 10, 10);
    protected final LocalDateTime start1 = LocalDateTime.of(2023, Month.AUGUST, 15, 10, 10);
    protected final Duration duration = Duration.ofDays(1);
    private final List<Task> emptyAssertList = Collections.emptyList();
    protected T taskManager;
    protected Task task;
    protected EpicTask epic;
    protected EpicTask emptyEpic;
    protected SubTask sub;
    protected Task timeTask;
    protected Task timeTask1;

    @BeforeEach
    void setUp() {
        task = new Task("t", "d");
        epic = new EpicTask("et", "ed");
        emptyEpic = new EpicTask("et1", "ed1");
        sub = new SubTask("st", "sd", epic.getId());
        timeTask = new Task("t", "d", start, duration);
        timeTask1 = new Task("t1", "d1", start1, duration);
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

    @Test
    @DisplayName("Check add task to prioritized set")
    void shouldTaskAddedToPrioritizedSet() {
        taskManager.addTask(task);
        assertThat(taskManager.getPrioritizedTasks().contains(task)).isEqualTo(true);
    }

    @Test
    @DisplayName("Check remove from prioritized set")
    void shouldTaskRemovedFromPrioritizedSet() {
        taskManager.addTask(task);
        taskManager.removeTask(task.getId());
        assertThat(taskManager.getPrioritizedTasks().contains(task)).isEqualTo(false);
    }

    @Test
    @DisplayName("Check order in prioritized set if added earlier task.")
    void shouldSecondTaskMustBeEarlierThanFirst() {
        taskManager.addTask(timeTask1);
        taskManager.addTask(timeTask);
        assertThat(taskManager.getPrioritizedTasks().indexOf(timeTask1)
                > taskManager.getPrioritizedTasks().indexOf(timeTask)).isEqualTo(true);

    }

    @Test
    @DisplayName("Check order in prioritized set if added later task.")
    void shouldFirstTaskMustBeEarlierThanSecond() {
        taskManager.addTask(timeTask);
        taskManager.addTask(timeTask1);
        assertThat(taskManager.getPrioritizedTasks().indexOf(timeTask1)
                > taskManager.getPrioritizedTasks().indexOf(timeTask)).isEqualTo(true);

    }

    @Test
    @DisplayName("Check order in prioritized set if added task start time is null.")
    void shouldFirstTaskEarlierThanAddedTaskWithNullStartTime() {
        taskManager.addTask(timeTask);
        taskManager.addTask(task);
        assertThat(taskManager.getPrioritizedTasks().indexOf(task)
                > taskManager.getPrioritizedTasks().indexOf(timeTask)).isEqualTo(true);
    }

    @Test
    @DisplayName("Check order in ptipritized set if contained task time is null")
    void shouldFirstTaskWithNullStartTimeLaterThanAddedTask() {
        taskManager.addTask(task);
        taskManager.addTask(timeTask);
        assertThat(taskManager.getPrioritizedTasks().indexOf(task)
                > taskManager.getPrioritizedTasks().indexOf(timeTask)).isEqualTo(true);
    }

    @Test
    @DisplayName("Check Epic updates after subtask update.")
    void shouldEpicStatusChangesAfterUpdateSubTaskStatus() {
        taskManager.addTask(epic);
        taskManager.addTask(sub);
        sub = new SubTask.Updater(sub).setTaskStatus(TaskStatus.DONE).update();
        taskManager.updateTask(sub);
        assertThat(taskManager.getTask(epic.getId()).getTaskStatus()).isEqualTo(TaskStatus.DONE);

    }

    @Test
    @DisplayName("Check Epics update after after transfer subtask.")
    void shouldStatusesChangeForBothEpicsWhenSubTaskFromFirstEpicTransferToSecondEpic() {
        taskManager.addTask(epic);
        taskManager.addTask(sub);
        taskManager.addTask(emptyEpic);
        sub = new SubTask.Updater(sub).setTaskStatus(TaskStatus.DONE)
                .setParentEpicTaskId(emptyEpic.getId()).update();
        taskManager.updateTask(sub);
        assertThat(taskManager.getTask(epic.getId()).getTaskStatus()).isEqualTo(TaskStatus.NEW);
        assertThat(taskManager.getTask(emptyEpic.getId()).getTaskStatus()).isEqualTo(TaskStatus.DONE);
    }

    @Test
    @DisplayName("Check Epic updates after removing subtask")
    void shouldEpicStatusUpdatesAfterSubTaskRemoving() {
        taskManager.addTask(epic);
        SubTask sub1 = new SubTask("st1", "sd1", epic.getId());
        taskManager.addTask(sub);
        taskManager.addTask(sub1);
        sub1 = new SubTask.Updater(sub1).setTaskStatus(TaskStatus.DONE).update();
        taskManager.updateTask(sub1);
        assertThat(taskManager.getTask(epic.getId()).getTaskStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        taskManager.removeTask(sub.getId());
        assertThat(taskManager.getTask(epic.getId()).getTaskStatus()).isEqualTo(TaskStatus.DONE);
    }
}
