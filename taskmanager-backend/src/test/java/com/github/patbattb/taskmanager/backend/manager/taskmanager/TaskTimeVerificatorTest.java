package com.github.patbattb.taskmanager.backend.manager.taskmanager;

import com.github.patbattb.taskmanager.backend.task.domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class TaskTimeVerificatorTest {

    private final LocalDateTime date = LocalDateTime.of(2023, Month.AUGUST, 10, 10, 10);
    private final LocalDateTime date1 = LocalDateTime.of(2023, Month.AUGUST, 12, 10, 10);
    private final LocalDateTime date2 = LocalDateTime.of(2023, Month.AUGUST, 15, 10, 10);
    private final Duration duration = Duration.ofDays(5);
    private final Duration duration1 = Duration.ofDays(10);
    private Task task;
    private Task task1;
    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = new DefaultTaskManager();
        task = new Task("t", "d");
        task1 = new Task("t1", "d1");
    }

    @Test
    @DisplayName("Check when first task before second.")
    void shouldReturnsFalseIfFirstTaskEndsBeforeSecondTaskStarts() {
        task = new Task.Updater(task).setStartTime(date2).setDuration(duration).update();
        manager.addTask(task);
        task1 = new Task.Updater(task1).setStartTime(date).setDuration(duration).update();
        assertThat(TaskTimeVerificator.findTimeOverlaps(manager, task1)).isEqualTo(false);
    }

    @Test
    @DisplayName("Check when first task after second.")
    void shouldReturnsFalseIfFirstTaskStartsAfterSecondTask() {
        task = new Task.Updater(task).setStartTime(date).setDuration(duration).update();
        task1 = new Task.Updater(task1).setStartTime(date2).setDuration(duration).update();
        manager.addTask(task);
        assertThat(TaskTimeVerificator.findTimeOverlaps(manager, task1)).isEqualTo(false);
    }

    @Test
    @DisplayName("Check when first end overlaps with second start.")
    void shouldReturnsTrueIfFirstEndLaterThanSecondStart() {
        task = new Task.Updater(task).setStartTime(date1).setDuration(duration).update();
        task1 = new Task.Updater(task1).setStartTime(date).setDuration(duration).update();
        manager.addTask(task);
        assertThat(TaskTimeVerificator.findTimeOverlaps(manager, task1)).isEqualTo(true);
    }

    @Test
    @DisplayName("Check when first start overlaps with second end.")
    void shouldReturnsTrueIfFirstStartEarlierThanSecondEnd() {
        task = new Task.Updater(task).setStartTime(date).setDuration(duration).update();
        task1 = new Task.Updater(task1).setStartTime(date1).setDuration(duration).update();
        manager.addTask(task);
        assertThat(TaskTimeVerificator.findTimeOverlaps(manager, task1)).isEqualTo(true);
    }

    @Test
    @DisplayName("Check when first starts earlier and ends later than second.")
    void shouldReturnsTrueIfFirstStartsEarlierAndEndsLater() {
        task = new Task.Updater(task).setStartTime(date1).setDuration(duration).update();
        task1 = new Task.Updater(task1).setStartTime(date).setDuration(duration1).update();
        manager.addTask(task);
        assertThat(TaskTimeVerificator.findTimeOverlaps(manager, task1)).isEqualTo(true);
    }

    @Test
    @DisplayName("Check when first starts later and ends earlier than second.")
    void shouldReturnsTrueIfFirstStartsLaterAndEndsEarlier() {
        task = new Task.Updater(task).setStartTime(date).setDuration(duration1).update();
        task1 = new Task.Updater(task1).setStartTime(date1).setDuration(duration).update();
        manager.addTask(task);
        assertThat(TaskTimeVerificator.findTimeOverlaps(manager, task1)).isEqualTo(true);
    }


}
