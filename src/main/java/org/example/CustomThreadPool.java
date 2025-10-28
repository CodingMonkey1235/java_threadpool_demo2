package org.example;

import java.util.LinkedList;
import java.util.Optional;

public class CustomThreadPool {
    private volatile boolean isRunning = true;
    private final Object monitor;
    private final CustomWorker[] workers;
    private final LinkedList<Runnable> tasks;

    public class CustomWorker extends Thread {

        public CustomWorker(String name) {
            super(name);
        }

        public void run() {
            while (isRunning) {
                Optional<Runnable> runnable;
                synchronized (monitor) {
                    while (tasks.isEmpty()) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Runnable task = tasks.getFirst();
                    tasks.remove(task);
                    runnable = Optional.of(task);
                }
                runnable.ifPresent(Runnable::run);
            }
        }
    }

    public CustomThreadPool(int poolSize) {
        monitor = new Object();
        tasks = new LinkedList<>();
        workers = new CustomWorker[poolSize];
        for (int i = 0; i < poolSize; i++) {
            workers[i] = new CustomWorker("Worker-" + (i + 1));
            workers[i].start();
        }
    }

    public void addTask(Runnable task) throws IllegalStateException {
        synchronized (monitor) {
            if (isRunning) {
                tasks.add(task);
                monitor.notifyAll();
            } else {
                throw new IllegalStateException("CustomThreadPool is not running");
            }
        }
    }

    public void shutdown() {
        isRunning = false;
    }
}
