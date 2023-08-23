package com.github.patbattb.taskmanager.backend.task;

import com.github.patbattb.taskmanager.backend.task.domain.EpicTask;
import com.github.patbattb.taskmanager.backend.task.domain.SubTask;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EpicUpdaterTest {

    private EpicTask epic;
    private SubTask sub;
    private SubTask sub1;
    @Mock
    private HashMap<Integer, SubTask> subTaskMap;

    @BeforeEach
    void setUp() {
        IdProvider.setStartId(0);
        epic = new EpicTask("EpicTitle", "EpicDescription");
        sub = new SubTask("SubTitle", "SubDescription", 0);
        sub1 = new SubTask("SubTitle1", "SubDescription1", 0);
    }

    @Test
    @DisplayName("Check update() epic with empty subtask list (after create) returns empty Optional")
    void shouldUpdateReturnsEmptyOptionalIfEpicAfterCreateDoesntHaveSubTasks() {
        Optional<EpicTask> opt = EpicUpdater.update(epic, subTaskMap);
        Assertions.assertThat(opt.isEmpty()).isEqualTo(true);
    }

    @Test
    @DisplayName("Check update() epic with empty subtask list (after remove) returns status NEW")
    void shouldUpdateReturnsEpicWithStatusNEWAfterRemoveSubtask() {
        epic = new EpicTask.Updater(epic).setTaskStatus(TaskStatus.DONE).update();
        Optional<EpicTask> opt = EpicUpdater.update(epic, subTaskMap);
        Assertions.assertThat(opt.isPresent()).isEqualTo(true);
        Assertions.assertThat(opt.get().getTaskStatus()).isEqualTo(TaskStatus.NEW);
    }


    @Test
    @DisplayName("Check update() with the statuses NEW in the all subtasks")
    void shouldUpdateReturnsNEWStatusWithNEWStatusInAllSubtasks() {
        sub = new SubTask.Updater(sub).setParentEpicTaskId(1).setTaskStatus(TaskStatus.NEW).update();
        sub1 = new SubTask.Updater(sub1).setParentEpicTaskId(1).setTaskStatus(TaskStatus.NEW).update();
        Mockito.when(subTaskMap.values()).thenReturn(List.of(sub, sub1));
        epic = new EpicTask.Updater(epic).setTaskStatus(TaskStatus.DONE).update();
        epic.getSubtaskIdList().add(2);
        epic.getSubtaskIdList().add(3);
        Optional<EpicTask> opt = EpicUpdater.update(epic, subTaskMap);
        Assertions.assertThat(opt.isPresent()).isEqualTo(true);
        Assertions.assertThat(opt.get().getTaskStatus()).isEqualTo(TaskStatus.NEW);
    }

    @Test
    @DisplayName("Check update() with the statuses DONE in the all subtasks")
    void shouldUpdateReturnsDONEStatusWithDONEStatusInAllSubTasks() {
        sub = new SubTask.Updater(sub).setParentEpicTaskId(1).setTaskStatus(TaskStatus.DONE).update();
        sub1 = new SubTask.Updater(sub1).setParentEpicTaskId(1).setTaskStatus(TaskStatus.DONE).update();
        Mockito.when(subTaskMap.values()).thenReturn(List.of(sub, sub1));
        epic.getSubtaskIdList().add(2);
        epic.getSubtaskIdList().add(3);
        Optional<EpicTask> opt = EpicUpdater.update(epic, subTaskMap);
        Assertions.assertThat(opt.isPresent()).isEqualTo(true);
        Assertions.assertThat(opt.get().getTaskStatus()).isEqualTo(TaskStatus.DONE);
    }

    @Test
    @DisplayName("Check update() with the statuses IN_PROGRESS in the all subtasks")
    void shouldUpdateReturnsINPROGRESSStatusWithINPROGRESSStatusInAllSubTasks() {
        sub = new SubTask.Updater(sub).setParentEpicTaskId(1).setTaskStatus(TaskStatus.IN_PROGRESS).update();
        sub1 = new SubTask.Updater(sub1).setParentEpicTaskId(1).setTaskStatus(TaskStatus.IN_PROGRESS).update();
        Mockito.when(subTaskMap.values()).thenReturn(List.of(sub, sub1));
        epic.getSubtaskIdList().add(2);
        epic.getSubtaskIdList().add(3);
        Optional<EpicTask> opt = EpicUpdater.update(epic, subTaskMap);
        Assertions.assertThat(opt.isPresent()).isEqualTo(true);
        Assertions.assertThat(opt.get().getTaskStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
    }

    @Test
    @DisplayName("Check updateStatus() with the statuses NEW and DONE in the subtasks")
    void souldUpdateReturnsINPROGRESSStatusWithNEWAndDONEStatusesInSubTasks() {
        sub = new SubTask.Updater(sub).setParentEpicTaskId(1).setTaskStatus(TaskStatus.NEW).update();
        sub1 = new SubTask.Updater(sub1).setParentEpicTaskId(1).setTaskStatus(TaskStatus.DONE).update();
        Mockito.when(subTaskMap.values()).thenReturn(List.of(sub, sub1));
        epic.getSubtaskIdList().add(2);
        epic.getSubtaskIdList().add(3);
        Optional<EpicTask> opt = EpicUpdater.update(epic, subTaskMap);
        Assertions.assertThat(opt.isPresent()).isEqualTo(true);
        Assertions.assertThat(opt.get().getTaskStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
    }
}
