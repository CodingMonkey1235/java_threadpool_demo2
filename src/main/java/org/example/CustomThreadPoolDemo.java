package org.example;

public class CustomThreadPoolDemo {
    public static void main(String[] args) {
        CustomThreadPool pool = new CustomThreadPool(3);

        for (int i = 1; i <= 9; i++) {
            int taskId = i;
            pool.addTask(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.println("Executing Task " + taskId + " by " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        try {
            Thread.sleep(5000);
            pool.shutdown();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // пытаемся запустить после shutdown()
//        for (int i = 10; i <= 19; i++) {
//            int taskId = i;
//            pool.addTask(() -> {
//                System.out.println("Executing Task " + taskId + " by " + Thread.currentThread().getName());
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//            });
//        }
    }
}
