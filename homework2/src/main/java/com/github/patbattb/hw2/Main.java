package com.github.patbattb.hw2;

import com.github.patbattb.hw2.domain.TaskStatus;
import com.github.patbattb.hw2.domain.task.EpicTask;
import com.github.patbattb.hw2.domain.task.SubTask;
import com.github.patbattb.hw2.domain.task.Task;
import com.github.patbattb.hw2.service.Managers;
import com.github.patbattb.hw2.service.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

@SuppressWarnings({"hideutilityclassconstructor", "magicnumber"}) //MainClass for test
public class Main {
    public static void main(String[] args) {
        LocalDateTime time = LocalDateTime.of(2023, Month.AUGUST, 11, 15, 30);
        Duration dur = Duration.ofDays(7);
        Task task = new Task("t", "d", time, dur);
        TaskManager manager = Managers.getDefaultTaskManager();
        manager.addTask(task);
        task = new Task.Updater(task).setTaskStatus(TaskStatus.IN_PROGRESS).setDuration(Duration.ofDays(14)).update();
        manager.updateTask(task);

        EpicTask epic = new EpicTask("et", "ed", time, dur);
        manager.addTask(epic);

        SubTask sub = new SubTask("st", "sd", time, dur, epic);
        manager.addTask(sub);

    }
}
