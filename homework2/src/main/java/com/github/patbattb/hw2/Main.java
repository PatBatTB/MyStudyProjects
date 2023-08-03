package com.github.patbattb.hw2;

import com.github.patbattb.hw2.domain.task.Task;
import com.github.patbattb.hw2.service.FileBackedTaskManager;
import com.github.patbattb.hw2.service.TaskManager;

@SuppressWarnings("hideutilityclassconstructor") //MainClass
public class Main {
    public static void main(String[] args) {
        var taskManager = new FileBackedTaskManager();
        taskManager.save();
    }

    private static void ph(TaskManager taskManager) {
        System.out.println();
        for (Task t : taskManager.history()) {
            System.out.println(t);
        }
    }

}
