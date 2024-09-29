package edu.andrewisnew.java.spring.lesson01.block7.bean_facrory;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigLauncher {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Bean bean = context.getBean(Bean.class);
        Bean bean2 = context.getBean(Bean.class);
        System.out.println(bean2 == bean);
        System.out.println(bean2.power() == bean.power());
    }
}
