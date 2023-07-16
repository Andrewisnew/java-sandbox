package edu.andrewisnew.java.topics.concurrency.lessons.lesson06;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/*
 Выгодно если мало потоков.
 */
public class Block2CASLockFreeStack {

    static class Stack<T> {
        private AtomicReference<Node<T>> top;


        public T pop() {
            Node<T> t;
            do {
                t = top.get();
                if (t == null) {
                    throw new NoSuchElementException();
                }
            } while (!top.compareAndSet(t, t.getNext()));
            return t.getValue();
        }

        public void push(T value) {
            Node<T> t;
            Node<T> newNode;
            do {
                t = top.get();
                newNode  = new Node<>(value, t);
            } while (!top.compareAndSet(t, newNode));
        }



        static class Node<T> {
            private T value;
            private Node<T> next;

            public Node(T value, Node<T> next) {
                this.value = value;
                this.next = next;
            }

            public Node(T value) {
                this.value = value;
            }

            public T getValue() {
                return value;
            }

            public Node<T> getNext() {
                return next;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Node<?> node = (Node<?>) o;
                return Objects.equals(value, node.value) && Objects.equals(next, node.next);
            }

            @Override
            public int hashCode() {
                return Objects.hash(value, next);
            }
        }
    }

}
