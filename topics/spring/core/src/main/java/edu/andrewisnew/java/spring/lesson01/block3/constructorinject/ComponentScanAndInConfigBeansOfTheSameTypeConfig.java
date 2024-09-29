package edu.andrewisnew.java.spring.lesson01.block3.constructorinject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("edu.andrewisnew.java.spring.lesson01.block3.constructorinject")
public class ComponentScanAndInConfigBeansOfTheSameTypeConfig {
    @Bean
    public Bean2 anotherBean2() {
        return new Bean2("anotherBean2");
    }
}
