package edu.andrewisnew.java.topics.concurrency.lessons.lesson06;

import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;

public class Block8Map {
    public static void main(String[] args) {
        Hashtable<Integer, String> integerStringHashtable = new Hashtable<>(); //read write synchronized на this
        HashMap<Object, Object> m = new HashMap<>(); //не потокобезопасная
        Map<Object, Object> objectObjectMap = Collections.synchronizedMap(m); //внутренний лок
        ConcurrentHashMap<Object, Object> objectObjectConcurrentHashMap = new ConcurrentHashMap<>();//cas + лок только на корзине
        ConcurrentSkipListMap<Object, Object> objectObjectConcurrentSkipListMap = new ConcurrentSkipListMap<>(); //так же как и предыдущая только отсортированная
    }
}
