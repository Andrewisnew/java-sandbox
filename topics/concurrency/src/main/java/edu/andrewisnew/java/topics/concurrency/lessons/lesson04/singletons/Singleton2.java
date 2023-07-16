package edu.andrewisnew.java.topics.concurrency.lessons.lesson04.singletons;

public class Singleton2 {
    public static volatile Singleton2 SINGLETON;

    private Singleton2() {
    }

    //synchronized - долго
    public synchronized static Singleton2 getSingleton() {
        if (SINGLETON == null) {
            return SINGLETON = new Singleton2();
        } else {
            return SINGLETON;
        }
    }
}
