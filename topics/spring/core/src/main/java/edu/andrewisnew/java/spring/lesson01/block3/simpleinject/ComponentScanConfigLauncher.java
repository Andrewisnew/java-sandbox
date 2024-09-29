package edu.andrewisnew.java.spring.lesson01.block3.simpleinject;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ComponentScanConfigLauncher {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ComponentScanConfig.class);
        System.out.println(context.getBean(Bean1.class));
    }
}
