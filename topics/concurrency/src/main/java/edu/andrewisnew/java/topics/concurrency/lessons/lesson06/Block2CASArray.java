package edu.andrewisnew.java.topics.concurrency.lessons.lesson06;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicReference;

/*
 Выгодно если мало потоков.
 */
public class Block2CASArray {
    public static void main(String[] args) {
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(new int[]{1, 2, 3});

        atomicIntegerArray.updateAndGet(2, x -> x * 2);
    }
}
