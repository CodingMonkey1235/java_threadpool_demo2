package org.example;

public class CustomThreadPool {
    private final BlockingQueue taskQueue;
    private final CustomWorker[] workers;
    static final Runnable STOP_SIGNAL = () -> { };
    private volatile boolean isRunning = true;

    public CustomThreadPool(int poolSize) {
        taskQueue = new BlockingQueue();
        workers = new CustomWorker[poolSize];
        for (int i = 0; i < poolSize; i++) {
            workers[i] = new CustomWorker(taskQueue, "Worker-" + (i + 1));
            workers[i].start();
        }
    }

    public void addTask(Runnable task) throws IllegalStateException {
        if (isRunning) {
            taskQueue.addTask(task);
        } else {
            throw new IllegalStateException("CustomThreadPool is not running");
        }
    }

    public void shutdown() {
        isRunning = false;
        for (int i = 0; i < workers.length; i++) {
            taskQueue.addTask(STOP_SIGNAL);
        }
    }
}
