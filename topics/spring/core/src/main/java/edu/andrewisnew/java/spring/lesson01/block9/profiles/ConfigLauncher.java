package edu.andrewisnew.java.spring.lesson01.block9.profiles;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigLauncher {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        ThemeBean bean = context.getBean(ThemeBean.class);
        System.out.println(bean.isLight());
    }
}
