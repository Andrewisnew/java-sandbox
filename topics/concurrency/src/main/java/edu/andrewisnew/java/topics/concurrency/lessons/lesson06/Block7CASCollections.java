package edu.andrewisnew.java.topics.concurrency.lessons.lesson06;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Block7CASCollections {
    public static void main(String[] args) {
        //неблокирующая. Работает с CAS
        ConcurrentLinkedQueue<Integer> integers = new ConcurrentLinkedQueue<>();

        ConcurrentLinkedDeque<String> strings = new ConcurrentLinkedDeque<>();
    }
}
