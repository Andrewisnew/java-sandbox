package edu.andrewisnew.java.topics.concurrency.lessons.lesson03.readwrite;

public interface Storage {
    String read();
    String write(String newVal);
}
