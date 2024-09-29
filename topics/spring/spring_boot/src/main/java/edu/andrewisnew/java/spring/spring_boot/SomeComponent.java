package edu.andrewisnew.java.spring.spring_boot;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SomeComponent {
    @PostConstruct
    private void init() {
        System.err.println("I'm here!");
    }
}
