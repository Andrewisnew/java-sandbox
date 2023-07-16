package edu.andrewisnew.java.topics.concurrency.lessons.lesson05.readwrite;

public interface Storage {
    String read();
    String write(String newVal);
}
