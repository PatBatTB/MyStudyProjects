package com.github.patbattb.taskmanager.backend.manager.historymanager;

import com.github.patbattb.taskmanager.backend.task.domain.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DefaultHistoryManager implements HistoryManager {

    private static final int MAXIMUM_SIZE = 10;
    private final Map<Integer, Node<Task>> historyMap;
    private int size;
    private Node<Task> head;
    private Node<Task> tail;


    public DefaultHistoryManager() {
        historyMap = new HashMap<>();
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(Task task) {
        Node<Task> newNode = new Node<>(null, task, null);
        if (historyMap.containsKey(task.getId())) {
            remove(task.getId());
        }
        historyMap.put(task.getId(), newNode);
        if (size == 0) {
            tail = newNode;
        } else {
            head.prev = newNode;
            newNode.next = head;
        }
        head = newNode;
        size++;
        if (size > MAXIMUM_SIZE) remove(tail.value.getId());
    }

    @Override
    public List<Task> getHistory() {
        List<Task> aList = new ArrayList<>();
        for (Node<Task> node = head; node != null; node = node.next) {
            aList.add(node.value);
        }
        return aList;
    }

    @Override
    public void remove(int id) {
        if (!historyMap.containsKey(id)) return;

        Node<Task> node = historyMap.get(id);

        if (node == head && node == tail) {
            head = null;
            tail = null;
        } else if (node == head) {
            node.next.prev = null;
            head = node.next;
        } else if (node == tail) {
            node.prev.next = null;
            tail = node.prev;
        } else {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }


        historyMap.remove(id);
        size--;
    }

    @SuppressWarnings("VisibilityModifier")
    private static class Node<Task> {
        Task value;
        Node<Task> next;
        Node<Task> prev;

        Node(Node<Task> prev, Task value, Node<Task> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
