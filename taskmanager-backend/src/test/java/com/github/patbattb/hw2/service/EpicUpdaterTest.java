package com.github.patbattb.hw2.service;

import com.github.patbattb.taskmanager.backend.domain.TaskStatus;
import com.github.patbattb.taskmanager.backend.domain.task.EpicTask;
import com.github.patbattb.taskmanager.backend.domain.task.SubTask;
import com.github.patbattb.taskmanager.backend.service.task.EpicUpdater;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EpicUpdaterTest {

    private EpicTask epic;
    private SubTask sub;
    private SubTask sub1;

    @BeforeEach
    void setUp() {
        epic = new EpicTask("EpicTitle", "EpicDescription");
        sub = new SubTask("SubTitle", "SubDescription", null);
        sub1 = new SubTask("SubTitle1", "SubDescription1", null);
    }

    @Test
    @DisplayName("Check updateStatus() with the empty list of the subtasks")
    void shouldUpdateReturnsNEWStatusWithEmptySubTasksList() {
        epic = EpicUpdater.updateStatus(epic);
        Assertions.assertThat(epic.getTaskStatus()).isEqualTo(TaskStatus.NEW);
    }

    @Test
    @DisplayName("Check updateStatus() with the statuses NEW in the all subtasks")
    void shouldUpdateReturnsNEWStatusWithNEWStatusInAllSubtasks() {
        sub = new SubTask.Updater(sub).setParentEpicTaskId(epic).setTaskStatus(TaskStatus.NEW).update();
        sub1 = new SubTask.Updater(sub1).setParentEpicTaskId(epic).setTaskStatus(TaskStatus.NEW).update();
        epic = EpicUpdater.updateStatus(epic);
        Assertions.assertThat(epic.getTaskStatus()).isEqualTo(TaskStatus.NEW);
    }

    @Test
    @DisplayName("Check updateStatus() with the statuses DONE in the all subtasks")
    void shouldUpdateReturnsDONEStatusWithDONEStatusInAllSubTasks() {
        sub = new SubTask.Updater(sub).setParentEpicTaskId(epic).setTaskStatus(TaskStatus.DONE).update();
        sub1 = new SubTask.Updater(sub1).setParentEpicTaskId(epic).setTaskStatus(TaskStatus.DONE).update();
        epic.getSubtaskIdList().put(sub.getId(), sub);
        epic.getSubtaskIdList().put(sub1.getId(), sub1);
        epic = EpicUpdater.updateStatus(epic);
        Assertions.assertThat(epic.getTaskStatus()).isEqualTo(TaskStatus.DONE);
    }

    @Test
    @DisplayName("Check updateStatus() with the statuses IN_PROGRESS in the all subtasks")
    void shouldUpdateReturnsINPROGRESSStatusWithINPROGRESSStatusInAllSubTasks() {
        sub = new SubTask.Updater(sub).setParentEpicTaskId(epic).setTaskStatus(TaskStatus.IN_PROGRESS).update();
        sub1 = new SubTask.Updater(sub1).setParentEpicTaskId(epic).setTaskStatus(TaskStatus.IN_PROGRESS).update();
        epic.getSubtaskIdList().put(sub.getId(), sub);
        epic.getSubtaskIdList().put(sub1.getId(), sub1);
        epic = EpicUpdater.updateStatus(epic);
        Assertions.assertThat(epic.getTaskStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
    }

    @Test
    @DisplayName("Check updateStatus() with the statuses NEW and DONE in the subtasks")
    void souldUpdateReturnsINPROGRESSStatusWithNEWAndDONEStatusesInSubTasks() {
        sub = new SubTask.Updater(sub).setParentEpicTaskId(epic).setTaskStatus(TaskStatus.NEW).update();
        sub1 = new SubTask.Updater(sub1).setParentEpicTaskId(epic).setTaskStatus(TaskStatus.DONE).update();
        epic.getSubtaskIdList().put(sub.getId(), sub);
        epic.getSubtaskIdList().put(sub1.getId(), sub1);
        epic = EpicUpdater.updateStatus(epic);
        Assertions.assertThat(epic.getTaskStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
    }
}
