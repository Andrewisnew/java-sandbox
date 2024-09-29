package edu.andrewisnew.java.spring.lesson01.block2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PropertySourceConfigLauncher {
    private static Logger log = LoggerFactory.getLogger(PropertySourceConfigLauncher.class);


    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PropertySourceConfig.class);

        for (String beanName : context.getBeanDefinitionNames()) {
            log.info("Bean " + beanName);
        }
    }
}
