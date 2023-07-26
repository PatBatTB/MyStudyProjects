package com.github.patbattb.hw2;

import com.github.patbattb.hw2.domain.task.EpicTask;
import com.github.patbattb.hw2.domain.task.SubTask;
import com.github.patbattb.hw2.domain.task.Task;
import com.github.patbattb.hw2.service.Managers;
import com.github.patbattb.hw2.service.TaskManager;

@SuppressWarnings("hideutilityclassconstructor") //MainClass
public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();
        Task task = new Task("t", "d");
        taskManager.addTask(task);

        EpicTask epic = new EpicTask("et", "ed");
        taskManager.addTask(epic);

        SubTask sub = new SubTask("st", "sd", epic);
        taskManager.addTask(sub);

        taskManager.getTask(1);
        taskManager.getTask(1);
        taskManager.getTask(1);
        taskManager.getTask(2);
        taskManager.getTask(3);
        taskManager.getTask(3);
        taskManager.getTask(3);
        taskManager.getTask(3);
        taskManager.getTask(3);
        taskManager.getTask(3);


        for (Task t : taskManager.history()) {
            System.out.println(t);
        }
    }
}
