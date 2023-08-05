package com.github.patbattb.hw2;

import com.github.patbattb.hw2.domain.TaskStatus;
import com.github.patbattb.hw2.domain.task.EpicTask;
import com.github.patbattb.hw2.domain.task.SubTask;
import com.github.patbattb.hw2.domain.task.Task;
import com.github.patbattb.hw2.service.Managers;
import com.github.patbattb.hw2.service.TaskManager;

@SuppressWarnings({"hideutilityclassconstructor", "magicnumber"}) //MainClass for test
public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getFileBackedTaskManager();

        var task = new Task("t", "d");
        taskManager.addTask(task);
        var task1 = new Task("t1", "d1");
        taskManager.addTask(task1);
        var task2 = new Task("t1", "d1");
        taskManager.addTask(task2);
        var task3 = new Task("t1", "d1");
        taskManager.addTask(task3);
        var task4 = new Task("t1", "d1");
        taskManager.addTask(task4);
        var task5 = new Task("t1", "d1");
        taskManager.addTask(task5);
        var task6 = new Task("t1", "d1");
        taskManager.addTask(task6);
        var task7 = new Task("t1", "d1");
        taskManager.addTask(task7);
        var task8 = new Task("t1", "d1");
        taskManager.addTask(task8);
        var task9 = new Task("t1", "d1");
        taskManager.addTask(task9);
        var epic = new EpicTask("et", "ed");
        taskManager.addTask(epic);
        var sub = new SubTask("st", "sd", epic);
        taskManager.addTask(sub);

        sub = new SubTask.Updater(sub).setTaskStatus(TaskStatus.DONE).update();
        taskManager.updateTask(sub);

        var sub1 = new SubTask("st1", "sd1", epic);
        taskManager.addTask(sub1);

        taskManager.getTask(1);
        taskManager.getTask(1);
        taskManager.getTask(1);
        taskManager.getTask(4);
        taskManager.getTask(3);
        taskManager.getTask(5);
        taskManager.getTask(5);
        taskManager.getTask(1);
        taskManager.getTask(1);
        taskManager.getTask(6);
        taskManager.getTask(7);
        taskManager.getTask(8);
        taskManager.getTask(9);
        taskManager.getTask(10);
        taskManager.getTask(11);
        taskManager.getTask(11);
        taskManager.getTask(1);
        taskManager.getTask(9);

        for (Task t : taskManager.getListOfAllTasks()) {
            System.out.println(t);
        }

        System.out.println("history:");
        for (Task t : taskManager.history()) {
            System.out.println(t);
        }
    }

    private static void ph(TaskManager taskManager) {
        System.out.println();
        for (Task t : taskManager.history()) {
            System.out.println(t);
        }
    }

}
