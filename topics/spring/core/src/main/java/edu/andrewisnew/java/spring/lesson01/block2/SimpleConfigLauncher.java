package edu.andrewisnew.java.spring.lesson01.block2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SimpleConfigLauncher {
    private static final Logger log = LoggerFactory.getLogger(SimpleConfigLauncher.class);


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SimpleConfig.class);
        for (String beanName : context.getBeanDefinitionNames()) {
            log.info("Bean " + beanName);
        }
    }
}
