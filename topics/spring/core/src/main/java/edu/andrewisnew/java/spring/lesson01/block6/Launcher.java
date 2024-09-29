package edu.andrewisnew.java.spring.lesson01.block6;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Launcher {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        context.registerShutdownHook();
        Bean1 bean = context.getBean(Bean1.class);
        System.out.println(bean.rangedValues());
    }
}
