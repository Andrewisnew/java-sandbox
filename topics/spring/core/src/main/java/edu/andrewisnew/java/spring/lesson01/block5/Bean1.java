package edu.andrewisnew.java.spring.lesson01.block5;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class Bean1 {
    @Autowired
    private Bean2 bean2;

    public Bean1() {
        System.err.println("Bean1 Init");
        System.err.println("Is null: " + bean2);//DI происходит после инстанциации
    }

    @PostConstruct
    private void hi() {
        System.err.println("PostConstruct");
        System.err.println("Is not null: " + bean2);
    }

    @PreDestroy
    private void bye() {
        System.err.println("PreDestroy");
    }
}
