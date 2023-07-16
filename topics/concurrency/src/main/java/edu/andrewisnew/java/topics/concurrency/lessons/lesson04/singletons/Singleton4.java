package edu.andrewisnew.java.topics.concurrency.lessons.lesson04.singletons;

public class Singleton4 {
    public static volatile Singleton4 SINGLETON;

    private static final Object lock = new Object();

    private Singleton4() {
    }

    public static Singleton4 getSingleton() { //хорошо, но можно короче
        Singleton4 local = SINGLETON;
        if (local != null) {
            return local;
        }
        synchronized (lock) {
            local = SINGLETON;
            if (local != null) return local;
            local = new Singleton4();
            return SINGLETON = local;
        }
    }
}

