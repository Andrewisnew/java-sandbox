package edu.andrewisnew.java.spring.lesson01.block2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;

public class FetchResourceLauncher {
    private static Logger log = LoggerFactory.getLogger(FetchResourceLauncher.class);


    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SimpleConfig.class);
        Resource resource = context.getResource("edu/andrewisnew/java/spring/lesson01/block2/FetchResourceLauncher.class");
        System.out.println(resource.exists());
        System.out.println(resource);
    }
}
