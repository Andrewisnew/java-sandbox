package edu.andrewisnew.java.spring.lesson01.block8.several_configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({edu.andrewisnew.java.spring.lesson01.block5.Config.class,
        edu.andrewisnew.java.spring.lesson01.block7.bean_facrory.Config.class})
public class Config {
}
