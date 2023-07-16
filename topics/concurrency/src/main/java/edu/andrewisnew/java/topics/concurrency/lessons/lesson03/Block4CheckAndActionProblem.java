package edu.andrewisnew.java.topics.concurrency.lessons.lesson03;

import java.util.List;

public class Block4CheckAndActionProblem {

    private static void process(List<String> list) {
        //synchronized (list) { //в остальных местах при модификации или чтении list тоже нужно синхронизироватся
            //check
            if (!list.isEmpty()) {
                //action
                System.out.println(list.get(0));
                list.clear();
            }
        //}
    }
}
