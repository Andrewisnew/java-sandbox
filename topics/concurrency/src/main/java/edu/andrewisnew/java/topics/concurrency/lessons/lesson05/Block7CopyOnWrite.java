package edu.andrewisnew.java.topics.concurrency.lessons.lesson05;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class Block7CopyOnWrite {
    public static void main(String[] args) {
        //лучше использовать когда много читателей, и мало писателей так как при каждом add пересоздаётся массив
        //при записи нового создаёт новый массив на основе предыдущего в "фоновом" режиме.
        // Операция чтения не блокирующая
        List<Object> objects = new CopyOnWriteArrayList<>();
        Set<Object> set = new CopyOnWriteArraySet<>();
    }
}
