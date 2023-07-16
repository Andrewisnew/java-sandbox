package edu.andrewisnew.java.topics.concurrency.lessons.lesson04.singletons;

public class Singleton6 {

    private Singleton6() {
    }

    public static Singleton6 getSingleton() { //хорошо, но можно короче
        return Singleton6Holder.SINGLETON;
    }

    private static class Singleton6Holder {
        static final Singleton6 SINGLETON = new Singleton6();
    }
}

