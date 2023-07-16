package edu.andrewisnew.java.topics.concurrency.lessons.lesson05;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Block6MyArrayBlockingQueue {

    //циклический буфер
    static class MyArrayBlockingQueue<E> {
        private final Object[] data;
        private final Lock lock;
        private final Condition notEmpty;
        private final Condition notFull;

        private int takeIndex = 0;
        private int putIndex = 0;
        private int size = 0;

        public MyArrayBlockingQueue(int size, boolean fair) {
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
                if (size == data.length) {
                    return false;
                }
                enqueue(element);
                return true;
            } finally {
                lock.unlock();
            }
        }

        private void enqueue(E element) {
            data[putIndex] = element;
            putIndex = (putIndex + 1) % data.length;
            size++;
            if (size == 1) {
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
                while (size == data.length) {
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
                while (size == data.length) {
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
                if (size == 0) {
                    return null;
                }
                //noinspection unchecked
                return (E) data[takeIndex];
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
                while (size == 0) {
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
                if (size == 0) {
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
                while (size == 0) {
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
            E val = (E) data[takeIndex];
            data[takeIndex] = null;
            takeIndex = (takeIndex + 1) % data.length;
            size--;
            if (size == data.length - 1) {
                notFull.signal();
            }
            return val;
        }
    }
}
