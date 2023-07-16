package edu.andrewisnew.java.topics.concurrency.lessons.lesson04;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

public class Block2CycleSingleThreadPoolExecutor {
    public static void main(String[] args) {
        Executor executor = new SingleThreadPoolExecutor();
        Runnable runnable = () -> {
            System.out.println("Start");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Hello");
        };

        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
    }

    static class SingleThreadPoolExecutor implements Executor {
        private final Queue<Runnable> tasks = new LinkedList<>();
        private final Thread thread;
        private volatile boolean isShutdown;

        public SingleThreadPoolExecutor() {
            thread = new Thread(() -> {
                while (!isShutdown) {
                    Runnable runnable;
                    synchronized (tasks) {
                        while (tasks.isEmpty()) {
                            try {
                                tasks.wait();
                            } catch (InterruptedException ignored) {
                                return;
                            }
                        }
                        runnable = tasks.remove();
                    }
                    runnable.run();
                }
            });
            thread.start();
        }

        public List<Runnable> shutdownNow() {
            if (!isShutdown) {
                synchronized (tasks) {
                    if (!isShutdown) {
                        thread.interrupt();
                        isShutdown = true;
                        return new ArrayList<>(tasks);
                    }
                }
            }
            return Collections.emptyList();
        }

        public void shutdown() {
            if (!isShutdown) {
                synchronized (tasks) {
                    if (!isShutdown) {
                        isShutdown = true;
                    }
                }
            }
        }

        public void execute(Runnable r) {
            if (isShutdown) {
                throw new RejectedExecutionException();
            }
            synchronized (tasks) {
                if (isShutdown) {
                    throw new RejectedExecutionException();
                }
                tasks.offer(r);
                tasks.notify();
            }
        }
    }
}

