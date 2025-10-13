package org.example;

public class CustomWorker extends Thread {
    private final BlockingQueue taskQueue;
    private volatile boolean isRunning = true;

    public CustomWorker(BlockingQueue queue, String name) {
        super(name);
        this.taskQueue = queue;
    }

    public void run() {
        while (isRunning) {
            Runnable task = taskQueue.getTask();
            if (task == CustomThreadPool.STOP_SIGNAL) {
                break;
            }
            task.run();
        }
    }

    public void shutdown() {
        isRunning = false;
        this.interrupt();
    }
}
