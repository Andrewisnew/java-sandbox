package edu.andrewisnew.java.spring.lesson01.block9.profiles;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dark")
public class DarkConfig {
    @Bean
    public ThemeBean themeBean() {
        return new ThemeBean(false);
    }
}
