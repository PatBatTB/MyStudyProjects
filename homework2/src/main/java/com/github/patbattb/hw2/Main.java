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
        EpicTask epic = new EpicTask("et", "ed");
        manager.addTask(epic);
        EpicTask epic1 = new EpicTask("et1", "ed1");
        manager.addTask(epic1);

        SubTask sub = new SubTask("st", "sd", epic);
        manager.addTask(sub);

        System.out.println(manager.getListOfAllTasks());

        sub = new SubTask.Updater(sub)
                .setTitle("nst")
                .setTaskStatus(TaskStatus.DONE)
                .update();
        manager.updateTask(sub);

        System.out.println(manager.getListOfAllTasks());

        sub = new SubTask.Updater(sub).setParentEpicTask(epic1).update();
        manager.updateTask(sub);

        System.out.println(manager.getListOfAllTasks());
    }
}
