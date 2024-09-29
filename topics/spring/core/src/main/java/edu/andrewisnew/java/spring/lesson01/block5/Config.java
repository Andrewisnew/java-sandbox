package edu.andrewisnew.java.spring.lesson01.block5;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("edu.andrewisnew.java.spring.lesson01.block5")
public class Config {
    @Bean(initMethod = "initialization")
    public Bean2 bean21() {
        return new Bean2();
    }
}
