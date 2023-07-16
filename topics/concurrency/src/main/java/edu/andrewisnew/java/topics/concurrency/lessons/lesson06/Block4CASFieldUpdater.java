package edu.andrewisnew.java.topics.concurrency.lessons.lesson06;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

/*
 Выгодно если мало потоков.
 */
public class Block4CASFieldUpdater {

    public static void main(String[] args) {
        AtomicIntegerFieldUpdater<A> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(A.class, "field");
        //как Atomic integer
    }


    static class A {
        volatile int field;
    }
}
