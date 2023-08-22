package com.github.patbattb.taskmanager.backend.service.manager;

import com.github.patbattb.taskmanager.backend.domain.TaskContainer;
import com.github.patbattb.taskmanager.backend.domain.task.EpicTask;
import com.github.patbattb.taskmanager.backend.domain.task.SubTask;
import com.github.patbattb.taskmanager.backend.domain.task.Task;
import com.github.patbattb.taskmanager.backend.service.task.EpicUpdater;
import com.github.patbattb.taskmanager.backend.service.task.TaskTimeVerificator;

import java.util.*;

/**
 * Default implementation of {@link TaskManager} interface
 */
public class DefaultTaskManager implements TaskManager {

    private final TaskContainer taskContainer;
    private final HistoryManager historyManager;
    private final TreeSet<Task> prioritizedTaskSet;

    public DefaultTaskManager() {
        taskContainer = new TaskContainer();
        historyManager = Managers.getDefaultHistory();
        prioritizedTaskSet = new TreeSet<>(((Comparator<Task>) (a, b) -> {
            if (Objects.isNull(a.getStartTime())) return 1;
            if (Objects.isNull(b.getStartTime())) return -1;
            return a.getStartTime().compareTo(b.getStartTime());
        }).thenComparingInt(Task::getId));
    }

    /**
     * Getter.
     *
     * @return the container with {@link HashMap} of {@link Task}s.
     */
    public TaskContainer getTaskContainer() {
        return taskContainer;
    }


    /**
     * Getter.
     *
     * @return {@link List} of all contained tasks (including epic-tasks and sub-tasks) in the manager.
     */
    @Override
    public List<Task> getListOfAllTasks() {
        List<Task> taskList = new ArrayList<>();
        taskList.addAll(taskContainer.getOrdinaryTaskMap().values());
        taskList.addAll(taskContainer.getEpicTaskMap().values());
        taskList.addAll(taskContainer.getSubTaskMap().values());
        return taskList;
    }

    /**
     * Getter.
     *
     * @return {@link List} of all contained {@link Task} in the manager.
     */
    @Override
    public List<Task> getListOfOrdinaryTasks() {
        return new ArrayList<>(taskContainer.getOrdinaryTaskMap().values());
    }

    /**
     * Getter.
     *
     * @return {@link List} of all contained {@link EpicTask} in the manager.
     */
    @Override
    public List<EpicTask> getListOfEpicTasks() {
        return new ArrayList<>(taskContainer.getEpicTaskMap().values());
    }

    /**
     * Getter.
     *
     * @param epic the epic task to get the subtasks into this.
     * @return {@link List} of all contained {@link SubTask} in the {@param epic}.
     */
    @Override
    public List<SubTask> getListOfSubTasks(EpicTask epic) {
        return taskContainer.getSubTaskMap().entrySet().stream()
                .filter(k -> epic.getSubtaskIdList().contains(k.getKey()))
                .map(Map.Entry::getValue)
                .toList();
    }

    /**
     * Getter.
     *
     * @param id Task's ID.
     * @return {@link Task} with the corresponding ID.
     */
    @Override
    public Task getTask(int id) {
        ArrayList<HashMap<Integer, ? extends Task>> aList = taskContainer.getListOfAllTaskMaps();
        for (HashMap<Integer, ? extends Task> map : aList) {
            for (Task task : map.values()) {
                if (task.getId() == id) {
                    historyManager.add(task);
                    return task;
                }
            }
        }
        return null;
    }


    /**
     * Removing a task with the corresponding ID from the manager.
     *
     * @param id task's ID.
     */
    @Override
    public void removeTask(int id) {
        var aList = taskContainer.getListOfAllTaskMaps();
        for (HashMap<Integer, ? extends Task> map : aList) {
            if (map.containsKey(id)) {
                Task removedTask = map.remove(id);
                historyManager.remove(id);
                if (removedTask instanceof SubTask removedSubTask) {
                    EpicTask epic = getTaskContainer().getEpicTaskMap().get(removedSubTask.getParentEpicTaskId());
                    epic.getSubtaskIdList().remove(removedSubTask.getId());
                    EpicUpdater.update(epic, taskContainer.getSubTaskMap()).ifPresent(this::updateTask);
                } else if (removedTask instanceof EpicTask removedEpicTask) {
                    for (int subTaskId : removedEpicTask.getSubtaskIdList()) {
                        taskContainer.getSubTaskMap().remove(subTaskId);
                        historyManager.remove(subTaskId);
                    }
                }
                return;
            }
        }
    }

    /**
     * Removing all tasks from the manager.
     */
    @Override
    public void removeAllTasks() {
        taskContainer.getOrdinaryTaskMap().clear();
        taskContainer.getEpicTaskMap().clear();
        taskContainer.getSubTaskMap().clear();
    }

    /**
     * Adding the arbitrary number of tasks to the manager.
     *
     * @param tasks to be added to the manager.
     */
    @Override
    public void addTask(Task... tasks) {
        for (Task t : tasks) {
            addTask(t);
        }
    }

    /**
     * Adding task to manager.
     *
     * @param task {@link Task} or it's inheritor to adding to the manager.
     */
    @Override
    public void addTask(Task task) {
        TaskTimeVerificator.findTimeOverlaps(this, task);
        if (task instanceof EpicTask epic) {
            addTask(epic);
        } else if (task instanceof SubTask subTask) {
            addTask(subTask);
        } else {
            taskContainer.getOrdinaryTaskMap().put(task.getId(), task);
            addTaskToPrioritizedTaskSet(task);
        }
    }

    private void addTask(EpicTask task) {
        taskContainer.getEpicTaskMap().put(task.getId(), task);
    }

    private void addTask(SubTask task) {
        EpicTask epic = taskContainer.getEpicTaskMap().get(task.getParentEpicTaskId());
        taskContainer.getSubTaskMap().put(task.getId(), task);
        addTaskToPrioritizedTaskSet(task);
        epic.getSubtaskIdList().add(task.getId());
        EpicUpdater.update(epic, taskContainer.getSubTaskMap()).ifPresent(this::updateTask);

    }

    /**
     * Updating task. Task from params overwrite task with the same ID in the manager.
     *
     * @param task updated version of exists task.
     */
    @Override
    public void updateTask(Task task) {
        TaskTimeVerificator.findTimeOverlaps(this, task);
        if (task instanceof EpicTask epic) {
            updateTask(epic);
        } else if (task instanceof SubTask sub) {
            updateTask(sub);
        } else {
            taskContainer.getOrdinaryTaskMap().put(task.getId(), task);
            addTaskToPrioritizedTaskSet(task);
        }
    }

    private void updateTask(EpicTask task) {
        taskContainer.getEpicTaskMap().put(task.getId(), task);
    }

    private void updateTask(SubTask task) {
        SubTask oldTask = taskContainer.getSubTaskMap().get(task.getId());
        EpicTask newEpic = taskContainer.getEpicTaskMap().get(task.getParentEpicTaskId());
        if (oldTask.getParentEpicTaskId() != task.getParentEpicTaskId()) {
            EpicTask oldEpic = taskContainer.getEpicTaskMap().get(oldTask.getParentEpicTaskId());
            oldEpic.getSubtaskIdList().remove(task.getId());
            newEpic.getSubtaskIdList().add(task.getId());
            EpicUpdater.update(oldEpic, taskContainer.getSubTaskMap()).ifPresent(this::updateTask);
        }
        taskContainer.getSubTaskMap().put(task.getId(), task);
        addTaskToPrioritizedTaskSet(task);
        EpicUpdater.update(newEpic, taskContainer.getSubTaskMap()).ifPresent(this::updateTask);
    }

    /**
     * Returns history of the viewed tasks.
     * Task is considered reviewed when it has been got through {@link TaskManager#getTask(int)} method.
     *
     * @return {@link List} of viewed tasks.
     */
    @Override
    public List<Task> history() {
        return historyManager.getHistory();
    }

    /**
     * Returns list of the tasks in the natural order for {@link Task#startTime} field.
     *
     * @return {@link List} of {@link Task} in the natural order.
     */
    @Override
    public List<Task> getPrioritizedTasks() {
        return List.copyOf(prioritizedTaskSet);
    }

    private void addTaskToPrioritizedTaskSet(Task task) {
        prioritizedTaskSet.removeIf(t -> task.getId() == t.getId());
        prioritizedTaskSet.add(task);
    }
}
