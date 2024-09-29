package edu.andrewisnew.java.spring.lesson01.block3.constructorinject;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ComponentScanAndInConfigBeansOfTheSameTypeConfigLauncher {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ComponentScanAndInConfigBeansOfTheSameTypeConfig.class);
        System.out.println(context.getBean(Bean1.class));
        //DefaultListableBeanFactory.java:1391
        //если подходят два, то берет бин с именем (имя класса с строчной буквы). Если не находит то NoUniqueBeanDefinitionException
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
    }
}
