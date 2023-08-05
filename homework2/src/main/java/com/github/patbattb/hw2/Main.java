package com.github.patbattb.hw2;

import com.github.patbattb.hw2.domain.task.Task;
import com.github.patbattb.hw2.service.FileBackedTaskManager;
import com.github.patbattb.hw2.service.TaskManager;

import java.io.IOException;

@SuppressWarnings("hideutilityclassconstructor") //MainClass
public class Main {
    public static void main(String[] args) throws IOException {
        var taskManager = new FileBackedTaskManager();

//        var task1 = new Task("t1", "d1");
//        taskManager.addTask(task1);
//
//        var task2 = new Task("t2", "d2");
//        taskManager.addTask(task2);
//
//        var task3 = new Task("t3", "d3");
//        taskManager.addTask(task3);
//
//        taskManager.save();

        taskManager.load();
        for (Task t : taskManager.getListOfAllTasks()) {
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
