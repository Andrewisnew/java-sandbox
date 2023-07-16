package edu.andrewisnew.java.topics.concurrency.lessons.lesson03.readwrite;

import java.util.HashMap;

public class Launcher {
    public static void main(String[] args) {
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();


        Storage storage = new MyFirstStorage();
        new Writer(storage, "Writer").start();
        for (int i = 0; i < 5; i++) {
            new Reader(storage, "Reader-" + i).start();
        }
    }
}
