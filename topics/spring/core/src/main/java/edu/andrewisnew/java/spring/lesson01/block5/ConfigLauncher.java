package edu.andrewisnew.java.spring.lesson01.block5;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigLauncher {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    }
}
