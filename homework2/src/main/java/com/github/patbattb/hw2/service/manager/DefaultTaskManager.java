package com.github.patbattb.hw2.service.manager;

import com.github.patbattb.hw2.domain.TaskContainer;
import com.github.patbattb.hw2.domain.task.EpicTask;
import com.github.patbattb.hw2.domain.task.SubTask;
import com.github.patbattb.hw2.domain.task.Task;
import com.github.patbattb.hw2.service.task.EpicUpdater;
import com.github.patbattb.hw2.service.task.TaskTimeVerificator;

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
     * @return {@link List} of all contained {@link EpicTask} in the manager.
     */
    @Override
    public List<EpicTask> getListOfEpicTasks() {
        return new ArrayList<>(taskContainer.getEpicTaskMap().values());
    }

    /**
     * Getter.
     * @param epic the epic task to get the subtasks into this.
     * @return {@link List} of all contained {@link SubTask} in the {@param epic}.
     */
    @Override
    public List<SubTask> getListOfSubTasks(EpicTask epic) {
        return new ArrayList<>(epic.getSubTasks().values());
    }

    /**
     * Getter.
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
     * @param id task's ID.
     */
    @Override
    public void removeTask(int id) {
        ArrayList<HashMap<Integer, ? extends Task>> aList = taskContainer.getListOfAllTaskMaps();
        for (HashMap<Integer, ? extends Task> map : aList) {
            if (map.containsKey(id)) {
                Task removedTask = map.remove(id);
                historyManager.remove(id);
                if (removedTask instanceof SubTask removedSubTask) {
                    EpicTask epic = removedSubTask.getParentEpicTask();
                    epic.getSubTasks().remove(removedSubTask.getId());
                    epic = EpicUpdater.updateStatus(epic);
                    updateTask(epic);
                } else if (removedTask instanceof EpicTask removedEpicTask) {
                    for (SubTask subTask : removedEpicTask.getSubTasks().values()) {
                        taskContainer.getSubTaskMap().remove(subTask.getId());
                        historyManager.remove(subTask.getId());
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
        EpicTask epic = task.getParentEpicTask();
        taskContainer.getSubTaskMap().put(task.getId(), task);
        addTaskToPrioritizedTaskSet(task);
        epic.getSubTasks().put(task.getId(), task);
        updateTask(task);

    }

    /**
     * Updating task. Task from params overwrite task with the same ID in the manager.
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
        for (SubTask subTask : task.getSubTasks().values()) {
            subTask = new SubTask.Updater(subTask).setParentEpicTask(task).update();
            task.getSubTasks().put(subTask.getId(), subTask);
            taskContainer.getSubTaskMap().put(subTask.getId(), subTask);
            addTaskToPrioritizedTaskSet(subTask);
        }
    }

    private void updateTask(SubTask task) {
        SubTask oldTask = taskContainer.getSubTaskMap().get(task.getId());
        EpicTask oldEpic = oldTask.getParentEpicTask();
        EpicTask newEpic = task.getParentEpicTask();
        taskContainer.getSubTaskMap().put(task.getId(), task);
        addTaskToPrioritizedTaskSet(task);
        if (oldEpic.getId() != newEpic.getId()) {
            oldEpic.getSubTasks().remove(task.getId());
            oldEpic = EpicUpdater.fullUpdate(oldEpic);
            updateTask(oldEpic);
        }
        newEpic.getSubTasks().put(task.getId(), task);
        newEpic = EpicUpdater.fullUpdate(newEpic);
        updateTask(newEpic);
    }

    /**
     * Returns history of the viewed tasks.
     * Task is considered reviewed when it has been got through {@link TaskManager#getTask(int)} method.
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
    public List<Task> priorityHistory() {
        return List.copyOf(prioritizedTaskSet);
    }

    private void addTaskToPrioritizedTaskSet(Task task) {
        prioritizedTaskSet.removeIf(t -> task.getId() == t.getId());
        prioritizedTaskSet.add(task);
    }
}
