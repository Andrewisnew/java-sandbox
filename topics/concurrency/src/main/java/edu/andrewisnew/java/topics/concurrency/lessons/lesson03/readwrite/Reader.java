package edu.andrewisnew.java.topics.concurrency.lessons.lesson03.readwrite;

import java.util.concurrent.TimeUnit;

public class Reader extends Thread {
    private final Storage storage;
    private final String name;

    public Reader(Storage storage, String name) {
        this.storage = storage;
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 30; i++) {
            String read = storage.read();
            System.out.println("[READER] " + name + " read: " + read);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
