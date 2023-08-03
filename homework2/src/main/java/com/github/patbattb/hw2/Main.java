package com.github.patbattb.hw2;

import com.github.patbattb.hw2.domain.TaskStatus;
import com.github.patbattb.hw2.domain.task.EpicTask;
import com.github.patbattb.hw2.domain.task.Task;
import com.github.patbattb.hw2.service.FileBackedTaskManager;
import com.github.patbattb.hw2.service.TaskManager;

import java.io.IOException;

@SuppressWarnings("hideutilityclassconstructor") //MainClass
public class Main {
    public static void main(String[] args) throws IOException {
        var taskManager = new FileBackedTaskManager();
        taskManager.save();

        var task = new EpicTask("title", "descr");
        taskManager.addTask(task);
        System.out.println(task);
        task = new EpicTask.Updater(task).setTaskStatus(TaskStatus.IN_PROGRESS).setDescription("newDescr").update();
        System.out.println(task);
    }

    private static void ph(TaskManager taskManager) {
        System.out.println();
        for (Task t : taskManager.history()) {
            System.out.println(t);
        }
    }

}
