package edu.andrewisnew.java.topics.concurrency.lessons.lesson03.philosophers;

public class Stick {
    private final long stickId;
    static long stickIdCounter = 1;
    public Stick() {
        stickId = stickIdCounter++;
    }

    public long getStickId() {
        return stickId;
    }

    @Override
    public String toString() {
        return "Stick{" +
                "stickId=" + stickId +
                '}';
    }
}
