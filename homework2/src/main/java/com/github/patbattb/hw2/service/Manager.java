package com.github.patbattb.hw2.service;

import com.github.patbattb.hw2.domain.TaskContainer;
import com.github.patbattb.hw2.domain.TaskStatus;
import com.github.patbattb.hw2.domain.task.EpicTask;
import com.github.patbattb.hw2.domain.task.SubTask;
import com.github.patbattb.hw2.domain.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A service for managing tasks.
 * Contains all tasks and methods for working with them.
 */
public final class Manager {

    private final TaskContainer taskContainer;

    public Manager() {
        taskContainer = new TaskContainer();
    }

    public TaskContainer getTaskContainer() {
        return taskContainer;
    }

    public ArrayList<Task> getListOfAllTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.addAll(taskContainer.getOrdinaryTaskMap().values());
        taskList.addAll(taskContainer.getEpicTaskMap().values());
        taskList.addAll(taskContainer.getSubTaskMap().values());
        return taskList;
    }

    public List<Task> getListOfOrdinaryTasks() {
        return new ArrayList<>(taskContainer.getOrdinaryTaskMap().values());
    }

    public List<EpicTask> getListOfEpicTasks() {
        return new ArrayList<>(taskContainer.getEpicTaskMap().values());
    }

    public List<SubTask> getListOfSubTasks(EpicTask epic) {
        return new ArrayList<>(epic.getSubTasks().values());
    }

    /**
     * Get task from container for ID.
     *
     * @param id Task's ID.
     * @return Task or null (if the task doesn't exist).
     */
    public Task getTask(int id) {
        ArrayList<HashMap<Integer, ? extends Task>> aList = new ArrayList<>();
        aList.add(taskContainer.getOrdinaryTaskMap());
        aList.add(taskContainer.getEpicTaskMap());
        aList.add(taskContainer.getSubTaskMap());
        for (HashMap<Integer, ? extends Task> map : aList) {
            for (Task task : map.values()) {
                if (task.getId() == id) {
                    return task;
                }
            }
        }
        return null;
    }

    /**
     * Remove task from container for ID.
     *
     * @param id task's ID.
     */
    public void removeTask(int id) {
        ArrayList<HashMap<Integer, ? extends Task>> aList = new ArrayList<>();
        aList.add(taskContainer.getOrdinaryTaskMap());
        aList.add(taskContainer.getEpicTaskMap());
        aList.add(taskContainer.getSubTaskMap());
        for (HashMap<Integer, ? extends Task> map : aList) {
            if (map.containsKey(id)) {
                Task removedTask = map.remove(id);
                if (removedTask instanceof SubTask removedSubTask) {
                    EpicTask newEpic = removedSubTask.getParentEpicTask();
                    newEpic.getSubTasks().remove(removedSubTask.getId());
                    EpicTask updateEpic = TaskUpdater.calculateEpicStatus(newEpic);
                    addTask(updateEpic);
                } else if (removedTask instanceof EpicTask removedEpicTask) {
                    for (SubTask subTask : removedEpicTask.getSubTasks().values()) {
                        taskContainer.getSubTaskMap().remove(subTask.getId());
                    }
                }
                return;
            }
        }
    }

    public void removeAllTasks() {
        taskContainer.getOrdinaryTaskMap().clear();
        taskContainer.getEpicTaskMap().clear();
        taskContainer.getSubTaskMap().clear();
    }

    public <T extends Task> void addTask(T task) {
        if (task.getClass() == EpicTask.class) {
            addEpicTask((EpicTask) task);
        } else if (task.getClass() == SubTask.class) {
            addSubTask((SubTask) task);
        } else {
            taskContainer.getOrdinaryTaskMap().put(task.getId(), task);
        }
    }

    private void addEpicTask(EpicTask task) {
        taskContainer.getEpicTaskMap().put(task.getId(), task);
    }

    private void addSubTask(SubTask task) {
        EpicTask epic = task.getParentEpicTask();
        taskContainer.getSubTaskMap().put(task.getId(), task);
        epic.getSubTasks().put(task.getId(), task);
        if (epic.getSubTasks().size() > 1) {
            updateTask(task, task.getTaskStatus());
        }

    }

    public void updateTask(Task task, TaskStatus status) {
        addTask(TaskUpdater.updateStatus(task, status));
    }

    public void updateTask(SubTask task, TaskStatus status) {
        SubTask newTask = TaskUpdater.updateStatus(task, status);
        EpicTask newEpic = newTask.getParentEpicTask();
        taskContainer.getSubTaskMap().put(newTask.getId(), newTask);
        newEpic.getSubTasks().put(newTask.getId(), newTask);
        addTask(newEpic);

    }

}
