package com.github.patbattb.hw2.service;

import com.github.patbattb.hw2.domain.TaskContainer;
import com.github.patbattb.hw2.domain.task.EpicTask;
import com.github.patbattb.hw2.domain.task.SubTask;
import com.github.patbattb.hw2.domain.task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Default implementation of {@link Manager} interface
 */
public final class DefaultManager implements Manager {

    private final TaskContainer taskContainer;

    public DefaultManager() {
        taskContainer = new TaskContainer();
    }

    public TaskContainer getTaskContainer() {
        return taskContainer;
    }

    @Override
    public ArrayList<Task> getListOfAllTasks() {
        ArrayList<Task> taskList = new ArrayList<>();
        taskList.addAll(taskContainer.getOrdinaryTaskMap().values());
        taskList.addAll(taskContainer.getEpicTaskMap().values());
        taskList.addAll(taskContainer.getSubTaskMap().values());
        return taskList;
    }

    @Override
    public List<Task> getListOfOrdinaryTasks() {
        return new ArrayList<>(taskContainer.getOrdinaryTaskMap().values());
    }

    @Override
    public List<EpicTask> getListOfEpicTasks() {
        return new ArrayList<>(taskContainer.getEpicTaskMap().values());
    }

    @Override
    public List<SubTask> getListOfSubTasks(EpicTask epic) {
        return new ArrayList<>(epic.getSubTasks().values());
    }

    @Override
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

    @Override
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

    @Override
    public void removeAllTasks() {
        taskContainer.getOrdinaryTaskMap().clear();
        taskContainer.getEpicTaskMap().clear();
        taskContainer.getSubTaskMap().clear();
    }

    @Override
    public void addTask(Task task) {
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
            updateTask(task);
        }

    }

    @Override
    public void updateTask(Task task) {
        if (task instanceof EpicTask epic) {
            updateTask(epic);
        } else if (task instanceof SubTask sub) {
            updateTask(sub);
        } else {
            taskContainer.getOrdinaryTaskMap().put(task.getId(), task);
        }
    }

    private void updateTask(EpicTask task) {
        taskContainer.getEpicTaskMap().put(task.getId(), task);
        for (SubTask subTask : task.getSubTasks().values()) {
            subTask = new SubTask.Updater(subTask).setParentEpicTask(task).update();
            task.getSubTasks().put(subTask.getId(), subTask);
            taskContainer.getSubTaskMap().put(subTask.getId(), subTask);
        }
    }

    private void updateTask(SubTask task) {
        SubTask oldTask = taskContainer.getSubTaskMap().get(task.getId());
        EpicTask oldEpic = oldTask.getParentEpicTask();
        EpicTask newEpic = task.getParentEpicTask();
        taskContainer.getSubTaskMap().put(task.getId(), task);
        if (oldEpic.getId() != newEpic.getId()) {
            oldEpic.getSubTasks().remove(task.getId());
            oldEpic = EpicStatusUpdater.updateStatus(oldEpic);
            updateTask(oldEpic);
        }
        newEpic.getSubTasks().put(task.getId(), task);
        newEpic = EpicStatusUpdater.updateStatus(newEpic);
        updateTask(newEpic);
    }
}
