package edu.andrewisnew.java.topics.concurrency.lessons.lesson03.philosophers;

import java.util.Objects;

public class Philosopher extends Thread {
    public Philosopher(String name) {
        super(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Philosopher that = (Philosopher) o;
        return Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
