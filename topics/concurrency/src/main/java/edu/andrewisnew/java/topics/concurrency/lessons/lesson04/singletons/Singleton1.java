package edu.andrewisnew.java.topics.concurrency.lessons.lesson04.singletons;

//не линивая инициализация
public class Singleton1 {
    public static final Singleton1 SINGLETON = new Singleton1();

    private Singleton1() {
    }
}
