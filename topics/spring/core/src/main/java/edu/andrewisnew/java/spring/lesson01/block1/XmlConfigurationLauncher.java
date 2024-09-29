package edu.andrewisnew.java.spring.lesson01.block1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class XmlConfigurationLauncher {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:lesson01/block1/config.xml");
        Rally rally = context.getBean(Rally.class);
        rally.start();
    }
}