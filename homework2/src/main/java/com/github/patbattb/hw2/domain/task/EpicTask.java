package com.github.patbattb.hw2.domain.task;

import com.github.patbattb.hw2.domain.TaskStatus;

import java.util.HashMap;

/**
 * An inheritor of {@link Task}.
 * A complex task than contains a list of {@link SubTask}.
 */
public final class EpicTask extends Task {
    private final HashMap<Integer, SubTask> subTasks;


    public EpicTask(String title, String description) {
        super(title, description);
        subTasks = new HashMap<>();
    }

    //TODO переделать TaskUpdater, что бы там использовались только готовые таски (не использовался конструктор)
    public EpicTask(int id, String title, String description,
                    TaskStatus taskStatus, HashMap<Integer, SubTask> subTasks) {
        super(id, title, description, taskStatus);
        this.subTasks = subTasks;
    }

    private EpicTask(Updater updater) {
        super(updater);
        this.subTasks = updater.subTasks;
    }

    public HashMap<Integer, SubTask> getSubTasks() {
        return subTasks;
    }

    @Override
    public String toString() {
        return "EpicTask{ subTasks.size=" + subTasks.size()
                + "} " + super.toString();
    }

    public static final class Updater extends Task.Updater {
        private final HashMap<Integer, SubTask> subTasks;

        public Updater(EpicTask task) {
            this.id = task.getId();
            this.title = task.getTitle();
            this.description = task.getDescription();
            this.taskStatus = task.getTaskStatus();
            this.subTasks = task.getSubTasks();
        }

        @Override
        public Updater setTitle(String title) {
            this.title = title;
            return this;
        }

        @Override
        public Updater setDescription(String description) {
            this.description = description;
            return this;
        }

        @Override
        public Updater setTaskStatus(TaskStatus taskStatus) {
            this.taskStatus = taskStatus;
            return this;
        }

        @Override
        public EpicTask update() {
            return new EpicTask(this);
        }
    }
}
