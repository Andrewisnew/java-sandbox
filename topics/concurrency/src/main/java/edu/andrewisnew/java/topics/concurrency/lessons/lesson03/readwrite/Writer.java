package edu.andrewisnew.java.topics.concurrency.lessons.lesson03.readwrite;

import java.util.concurrent.TimeUnit;

public class Writer extends Thread {
    private final Storage storage;
    private final String name;

    public Writer(Storage storage, String name) {
        this.storage = storage;
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            String newVal = i + " " + name + " written that he have " + i + " ideas";
            String writtenBefore = storage.write(newVal);
            System.out.println("[WRITER] " + name + " rewrite from: " + writtenBefore + ", to: " + newVal);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
