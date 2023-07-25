package com.github.patbattb.hw2;

import com.github.patbattb.hw2.domain.TaskStatus;
import com.github.patbattb.hw2.domain.task.EpicTask;
import com.github.patbattb.hw2.domain.task.SubTask;
import com.github.patbattb.hw2.service.DefaultManager;
import com.github.patbattb.hw2.service.Manager;

@SuppressWarnings("hideutilityclassconstructor") //MainClass
public class Main {
    public static void main(String[] args) {
        Manager manager = new DefaultManager();
        var epic = new EpicTask("t", "d");
        manager.addTask(epic);
        System.out.println(manager.getListOfAllTasks());
        var sub = new SubTask("st", "sd", epic);
        manager.addTask(sub);
        System.out.println(manager.getListOfAllTasks());

        sub = new SubTask.Updater(sub).setTaskStatus(TaskStatus.DONE).update();
        manager.updateTask(sub);
        System.out.println(manager.getListOfAllTasks());

        var sub1 = new SubTask("st1", "sd1", epic);
        manager.addTask(sub1);
        System.out.println(manager.getListOfAllTasks());
    }
}
