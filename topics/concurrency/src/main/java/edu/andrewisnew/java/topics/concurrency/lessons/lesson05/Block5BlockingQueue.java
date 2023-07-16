package edu.andrewisnew.java.topics.concurrency.lessons.lesson05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

//перепишем {@link edu.andrewisnew.java.topics.concurrency.lessons.lesson04.Block2CycleSingleThreadPoolExecutor}
public class Block5BlockingQueue {

    private static class BlockingQueue<E> {
        private final LinkedList<E> delegate = new LinkedList<>();

        public E take() throws InterruptedException {
            synchronized (delegate) {
                while (delegate.isEmpty()) {
                    delegate.wait();
                }
                return delegate.removeFirst();
            }
        }

        public boolean add(E element) {
            synchronized (delegate) {
                delegate.offer(element);
                delegate.notify();
            }
            return true;
        }
    }

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
        private final BlockingQueue<Runnable> tasks = new BlockingQueue<>();
        private final Thread thread;
        private volatile boolean isShutdown;

        public SingleThreadPoolExecutor() {
            thread = new Thread(() -> {
                while (!isShutdown) {
                    try {
                        Runnable runnable = tasks.take();
                        runnable.run();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            thread.start();
        }

        public List<Runnable> shutdownNow() {
            if (!isShutdown) {
                synchronized (tasks.delegate) {
                    if (!isShutdown) {
                        thread.interrupt();
                        isShutdown = true;
                        return new ArrayList<>(tasks.delegate);
                    }
                }
            }
            return Collections.emptyList();
        }

        public void shutdown() {
            if (!isShutdown) {
                synchronized (tasks.delegate) {
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
            tasks.add(r);
        }
    }
}

