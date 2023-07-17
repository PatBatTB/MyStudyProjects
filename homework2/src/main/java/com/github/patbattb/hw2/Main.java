package com.github.patbattb.hw2;

import com.github.patbattb.hw2.domain.TaskStatus;
import com.github.patbattb.hw2.domain.task.EpicTask;
import com.github.patbattb.hw2.domain.task.SubTask;
import com.github.patbattb.hw2.domain.task.Task;
import com.github.patbattb.hw2.service.Manager;

@SuppressWarnings("hideutilityclassconstructor") //MainClass
public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        Task task1 = new Task("ordTitle1", "ordDescr1");
        manager.addTask(task1);
        Task task2 = new Task("ordTitle2", "ordDescr2");
        manager.addTask(task2);
        EpicTask epic1 = new EpicTask("epicTitle1", "EpicDescr1");
        manager.addTask(epic1);
        EpicTask epic2 = new EpicTask("epicTitle2", "epicDescr2");
        manager.addTask(epic2);
        System.out.println(manager.getListOfAllTasks()); //2 ordTasks (NEW), 2 epics (NEW)

        SubTask sub11 = new SubTask("subTitle11", "subDescr11", epic1);
        SubTask sub21 = new SubTask("subTitle21", "subDescr21", epic2);
        SubTask sub22 = new SubTask("subTitle22", "subDescr22", epic2);
        manager.addTask(sub11);
        manager.addTask(sub21);
        manager.addTask(sub22);
        System.out.println(manager.getListOfAllTasks()); //1 epic with 1 sub, 1 epic with 2 subs (all NEW)

        manager.updateTask(task1, TaskStatus.IN_PROGRESS);
        manager.updateTask(task2, TaskStatus.DONE);
        manager.updateTask(sub11, TaskStatus.IN_PROGRESS);
        manager.updateTask(sub22, TaskStatus.DONE);
        System.out.println(manager.getListOfAllTasks()); //1 ord (IN_PROGRESS), 1 ord (DONE), 2 epic (IN_PROGRESS)

        manager.removeTask(1); // ord
        manager.removeTask(3); // epic
        manager.removeTask(6); //sub
        System.out.println(manager.getListOfAllTasks()); //1 ord (DONE), 1 epic with 1 sub (DONE)

        Task task = new Task("title", "description");
        System.out.println(task);

        task = new Task.Updater(task)
                .setTaskStatus(TaskStatus.IN_PROGRESS)
                .update();
        System.out.println(task);


        EpicTask epic = new EpicTask("epicT", "epicD");
        EpicTask epic12 = new EpicTask("epicT", "epicD");
        System.out.println(epic);

        epic = new EpicTask.Updater(epic)
                .setTaskStatus(TaskStatus.IN_PROGRESS)
                .update();
        System.out.println(epic);

        SubTask sub = new SubTask("subT", "subD", epic);
        System.out.println(sub);
        sub = new SubTask.Updater(sub)
                .setDescription("newD")
                .setTitle("newT")
                .setTaskStatus(TaskStatus.DONE)
                .setParentEpicTask(epic12)
                .update();
        System.out.println(sub);
    }
}
