package edu.andrewisnew.java.spring.lesson01.block3.setterinject;

import org.springframework.stereotype.Component;

@Component
public class Bean2 {
    private String name;

    public Bean2(String name) {
        this.name = name;
    }

    public Bean2() {
        this.name = "default";
    }

    @Override
    public String toString() {
        return "Bean2{" +
                "name='" + name + '\'' +
                '}';
    }
}
