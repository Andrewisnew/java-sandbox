package edu.andrewisnew.java.spring.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

public class Launcher {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        NewsProvider newsProvider = context.getBean(NewsProvider.class);
        newsProvider.saveNews("cool news");
        newsProvider.showLastNews(1);
        try {
            newsProvider.showNews(4);
        } catch (Exception ignored) {
        }
    }

    @ComponentScan
    @EnableAspectJAutoProxy
    public static class Config {
    }
}
