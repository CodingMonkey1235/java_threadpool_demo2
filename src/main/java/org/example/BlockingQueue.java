package org.example;

import java.util.LinkedList;

public class BlockingQueue {
    private final LinkedList<Runnable> tasks;

    public BlockingQueue() {
        tasks = new LinkedList<>();
    }

    public synchronized void addTask(Runnable task) {
        tasks.add(task);
        notify();
    }

    public synchronized Runnable getTask() {
        while (tasks.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Runnable task = tasks.getFirst();
        tasks.remove(task);
        return task;
    }
}
