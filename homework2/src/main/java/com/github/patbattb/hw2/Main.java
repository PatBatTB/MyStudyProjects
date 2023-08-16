package com.github.patbattb.hw2;

import com.github.patbattb.hw2.domain.task.EpicTask;
import com.github.patbattb.hw2.domain.task.SubTask;
import com.github.patbattb.hw2.domain.task.Task;
import com.github.patbattb.hw2.service.manager.Managers;
import com.github.patbattb.hw2.service.manager.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;


@SuppressWarnings({"hideutilityclassconstructor", "magicnumber"}) //MainClass for test
public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefaultTaskManager();
        Task task = new Task("t", "d", LocalDateTime.now(), Duration.ofDays(1));
        Task task1 = new Task("t1", "d1", LocalDateTime.now().plus(Duration.ofDays(1)), Duration.ofDays(2));
        Task task2 = new Task("t2", "d2", LocalDateTime.now().minus(Duration.ofDays(1)), Duration.ofDays(2));
        Task task3 = new Task("t3", "d3");
        Task task4 = new Task("t4", "d4");
        EpicTask epic = new EpicTask("et", "ed");
        SubTask sub = new SubTask("st", "sd", LocalDateTime.now().plus(Duration.ofDays(3)), Duration.ofDays(5), epic);
        manager.addTask(task, task1, task2, task3, task4, epic, sub);
        manager.getTask(1);
        manager.getTask(5);
        manager.getTask(2);
        manager.getTask(3);
        manager.getTask(4);
        manager.getTask(7);
        manager.getTask(6);

        manager.getListOfAllTasks().forEach(System.out::println);
        System.out.println();
        manager.history().forEach(System.out::println);
        System.out.println();
        manager.priorityHistory().forEach(System.out::println);

        System.out.println("___");
        task = new Task.Updater(task).setStartTime(LocalDateTime.now().plus(Duration.ofHours(10)))
                .setDuration(Duration.ofDays(10)).update();
        manager.updateTask(task);
        manager.priorityHistory().forEach(System.out::println);
    }
}
