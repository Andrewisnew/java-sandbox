package edu.andrewisnew.java.topics.concurrency.lessons.lesson05;

import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Block6MyArrayBlockingStack {

    public static void main(String[] args) {
        MyArrayBlockingStack<Integer> stack = new MyArrayBlockingStack<>(4, false);
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        ExecutorService executorService2 = Executors.newSingleThreadExecutor();

        executorService1.execute(() -> putWithoutException(stack, 1));
        executorService2.execute(() -> putWithoutException(stack, 2));
        executorService1.execute(() -> putWithoutException(stack, 3));
        executorService2.execute(() -> putWithoutException(stack, 4));
        executorService1.execute(() -> putWithoutException(stack, 5));
        executorService2.execute(() -> takeWithoutException(stack));
        executorService1.execute(() -> takeWithoutException(stack));
        executorService1.execute(() -> putWithoutException(stack, 6));
        executorService1.execute(() -> putWithoutException(stack, 7));
        executorService1.execute(() -> putWithoutException(stack, 8));
        executorService2.execute(() -> takeWithoutException(stack));
    }

    private static void putWithoutException(MyArrayBlockingStack<Integer> stack, int element) {
        try {
            stack.put(element);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void takeWithoutException(MyArrayBlockingStack<Integer> stack) {
        try {
            System.out.println(stack.take());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static class MyArrayBlockingStack<E> {
        private final Object[] data;
        private final Lock lock;
        private final Condition notEmpty;
        private final Condition notFull;
        private int putIndex = 0;

        public MyArrayBlockingStack(int size, boolean fair) {
            data = new Object[size];
            lock = new ReentrantLock(fair);
            notEmpty = lock.newCondition();
            notFull = lock.newCondition();
        }

        /**
         * Добавляет элемент при наличии места
         *
         * @return {@code true} если удачно
         */
        public boolean offer(E element) {
            lock.lock();
            try {
                if (putIndex == data.length) {
                    return false;
                }
                enqueue(element);
                return true;
            } finally {
                lock.unlock();
            }
        }

        private void enqueue(E element) {
            data[putIndex++] = element;
            if (putIndex == 1) {
                notEmpty.signal();
            }
        }

        /**
         * Добавляет элемент при наличии места
         *
         * @return {@code true} если удачно
         * @throws IllegalStateException заполнена
         */
        public boolean add(E element) {
            if (offer(element)) {
                return true;
            }
            throw new IllegalStateException("Queue is full");
        }

        /**
         * Добавляет элемент. Ждет если нет места
         */
        public void put(E element) throws InterruptedException {
            lock.lockInterruptibly();
            try {
                while (putIndex == data.length) {
                    notFull.await();
                }
                enqueue(element);
            } finally {
                lock.unlock();
            }
        }

        /**
         * Добавляет элемент. Ждет если нет места
         */
        public boolean offer(E element, long timeout, TimeUnit timeUnit) throws InterruptedException {
            long nanos = timeUnit.toNanos(timeout);
            lock.lock();
            try {
                while (putIndex == data.length) {
                    if (nanos <= 0L)
                        return false;
                    nanos = notFull.awaitNanos(nanos);
                }
                enqueue(element);
                return true;
            } finally {
                lock.unlock();
            }
        }

        public E peek() {
            lock.lock();
            try {
                if (putIndex == 0) {
                    return null;
                }
                //noinspection unchecked
                return (E) data[putIndex];
            } finally {
                lock.unlock();
            }
        }

        public E element() {
            E peek = peek();
            if (peek == null) {
                throw new NoSuchElementException("Queue is empty");
            }
            return peek;
        }

        public E take() throws InterruptedException {
            lock.lockInterruptibly();
            try {
                while (putIndex == 0) {
                    notEmpty.await();
                }
                return dequeue();
            } finally {
                lock.unlock();
            }
        }

        public E poll() {
            lock.lock();
            try {
                if (putIndex == 0) {
                    return null;
                }
                return dequeue();
            } finally {
                lock.unlock();
            }
        }

        public E remove() {
            E element = poll();
            if (element == null) {
                throw new NoSuchElementException("Queue is empty");
            }
            return element;
        }

        public E poll(long timeout, TimeUnit timeUnit) throws InterruptedException {
            long nanos = timeUnit.toNanos(timeout);
            lock.lock();
            try {
                while (putIndex == 0) {
                    if (nanos <= 0) {
                        return null;
                    }
                    nanos = notEmpty.awaitNanos(nanos);
                }
                return dequeue();
            } finally {
                lock.unlock();
            }
        }

        private E dequeue() {
            //noinspection unchecked
            E val = (E) data[--putIndex];
            data[putIndex] = null;
            if (putIndex == data.length - 1) {
                notFull.signal();
            }
            return val;
        }
    }
}
