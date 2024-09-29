package edu.andrewisnew.java.spring.spring_boot.nested;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SomeMoreComponent {
    @PostConstruct
    private void init() {
        System.err.println("I'm here too!");
    }
}
