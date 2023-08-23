package com.github.patbattb.taskmanager.backend.manager.taskmanager;

import com.github.patbattb.taskmanager.backend.manager.Managers;
import com.github.patbattb.taskmanager.backend.manager.historymanager.HistoryManager;
import com.github.patbattb.taskmanager.backend.task.EpicUpdater;
import com.github.patbattb.taskmanager.backend.task.domain.EpicTask;
import com.github.patbattb.taskmanager.backend.task.domain.SubTask;
import com.github.patbattb.taskmanager.backend.task.domain.Task;

import java.util.*;

/**
 * Default implementation of {@link TaskManager} interface
 */
@SuppressWarnings("AvoidStarImport")
public class DefaultTaskManager implements TaskManager {

    private final TaskContainer taskContainer;
    private final HistoryManager historyManager;
    private final TreeSet<Task> prioritizedTaskSet;

    public DefaultTaskManager() {
        taskContainer = new TaskContainer();
        historyManager = Managers.getDefaultHistory();
        prioritizedTaskSet = new TreeSet<>(((Comparator<Task>) (a, b) -> {
            if (Objects.equals(a, b)) return 0;
            if (Objects.isNull(a.getStartTime())) return 1;
            if (Objects.isNull(b.getStartTime())) return -1;
            return a.getStartTime().compareTo(b.getStartTime());
        }).thenComparingInt(Task::getId));
    }

    /**
     * Getter.
     *
     * @return the container with {@code HashMap} of {@link Task}s.
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
        Optional<? extends Task> taskOptional = taskContainer.getListOfAllTaskMaps().stream()
                .flatMap(p -> p.entrySet().stream())
                .filter(p -> p.getKey().equals(id))
                .map(Map.Entry::getValue)
                .findFirst();

        if (taskOptional.isPresent()) {
            historyManager.add(taskOptional.get());
            return taskOptional.get();
        } else return null;
    }


    /**
     * Removing a task with the corresponding ID from the manager.
     *
     * @param id task's ID.
     */
    @Override
    public Task removeTask(int id) {
        Optional<? extends Task> optionalTask = taskContainer.getListOfAllTaskMaps().stream()
                .filter(map -> map.containsKey(id))
                .map(map -> map.remove(id))
                .findFirst();

        if (optionalTask.isEmpty()) return null;
        prioritizedTaskSet.remove(optionalTask.get());
        historyManager.remove(id);
        if (optionalTask.get() instanceof SubTask subTask) {
            EpicTask epic = getTaskContainer().getEpicTaskMap().get(subTask.getParentEpicTaskId());
            epic.getSubtaskIdList().remove(Integer.valueOf(subTask.getId()));
            EpicUpdater.update(epic, taskContainer.getSubTaskMap()).ifPresent(this::updateTask);
        } else if (optionalTask.get() instanceof EpicTask epicTask) {
            for (int subTaskId : epicTask.getSubtaskIdList()) {
                prioritizedTaskSet.remove(taskContainer.getSubTaskMap().get(subTaskId));
                taskContainer.getSubTaskMap().remove(subTaskId);
                historyManager.remove(subTaskId);
            }
        }
        return optionalTask.get();
    }

    /**
     * Removing all tasks from the manager.
     */
    @Override
    public void removeAllTasks() {
        taskContainer.getOrdinaryTaskMap().clear();
        taskContainer.getEpicTaskMap().clear();
        taskContainer.getSubTaskMap().clear();
        prioritizedTaskSet.clear();
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
            prioritizedSetAdd(task);
        }
    }

    private void addTask(EpicTask task) {
        taskContainer.getEpicTaskMap().put(task.getId(), task);
    }

    private void addTask(SubTask task) {
        EpicTask epic = taskContainer.getEpicTaskMap().get(task.getParentEpicTaskId());
        taskContainer.getSubTaskMap().put(task.getId(), task);
        prioritizedSetAdd(task);
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
            prioritizedSetAdd(task);
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
            oldEpic.getSubtaskIdList().remove(Integer.valueOf(task.getId()));
            newEpic.getSubtaskIdList().add(task.getId());
            EpicUpdater.update(oldEpic, taskContainer.getSubTaskMap()).ifPresent(this::updateTask);
        }
        taskContainer.getSubTaskMap().put(task.getId(), task);
        prioritizedSetAdd(task);
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

    private void prioritizedSetAdd(Task task) {
        prioritizedSetRemove(task);
        prioritizedTaskSet.add(task);
    }

    private void prioritizedSetRemove(Task task) {
        prioritizedTaskSet.remove(task);
    }
}
