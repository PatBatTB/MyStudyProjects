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
        EpicTask epic = new EpicTask("t", "d");
        taskManager.addTask(epic);
        SubTask sub = new SubTask("st", "sd", epic);
        taskManager.addTask(sub);
        SubTask sub1 = new SubTask("st1", "sd1", epic);
        taskManager.addTask(sub1);
        SubTask sub2 = new SubTask("st2", "sd2", epic);
        taskManager.addTask(sub2);

        EpicTask epic1 = new EpicTask("t1", "d1");
        taskManager.addTask(epic1);

        taskManager.getTask(1);
        taskManager.getTask(2);
        taskManager.getTask(3);
        taskManager.getTask(4);
        taskManager.getTask(5);
        taskManager.getTask(1);
        taskManager.getTask(1);
        taskManager.getTask(3);
        taskManager.getTask(3);
        taskManager.getTask(5);
        taskManager.getTask(2);
        taskManager.getTask(1);

        ph(taskManager);

        taskManager.removeTask(2);
        ph(taskManager);

        taskManager.removeTask(1);
        ph(taskManager);
    }

    private static void ph(TaskManager taskManager) {
        System.out.println();
        for (Task t : taskManager.history()) {
            System.out.println(t);
        }
    }

}
