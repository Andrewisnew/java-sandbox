package edu.andrewisnew.java.spring.lesson01.block5;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class Bean2 {
    private final int power;

    public Bean2() {
        power = ThreadLocalRandom.current().nextInt();
    }

    public int power() {
        return power;
    }

    @PostConstruct
    public void iniiiiit() {
        System.err.println("Iniiiit");
    }

    public void initialization() {
        System.err.println("Bean2 initialization");
    }

}
