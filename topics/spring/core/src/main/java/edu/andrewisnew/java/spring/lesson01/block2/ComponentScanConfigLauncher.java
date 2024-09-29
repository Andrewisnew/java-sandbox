package edu.andrewisnew.java.spring.lesson01.block2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ComponentScanConfigLauncher {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ComponentScanConfig.class);
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
    }
}
