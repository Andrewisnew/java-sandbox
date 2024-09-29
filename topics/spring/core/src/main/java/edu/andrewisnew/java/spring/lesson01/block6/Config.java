package edu.andrewisnew.java.spring.lesson01.block6;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;

import java.util.Set;

@Configuration
@ComponentScan("edu.andrewisnew.java.spring.lesson01.block6")
public class Config {
    @Autowired
    private RangedValuesConverter rangedValuesConverter;

    @Autowired
    private RangedValuesFormatter rangedValuesFormatter;

    @Bean
    ConversionService conversionService(ConversionServiceFactoryBean factory) {
        return factory.getObject();
    }

//    @Bean
//    ConversionService conversionService(FormattingConversionServiceFactoryBean factory) {
//        return factory.getObject();
//    }

    @Bean
    ConversionServiceFactoryBean conversionServiceFactoryBean() { //implements FactoryBean
        ConversionServiceFactoryBean factory = new ConversionServiceFactoryBean();
        factory.setConverters(Set.of(rangedValuesConverter));
        return factory;
    }

    @Bean
    FormattingConversionServiceFactoryBean formattingConversionServiceFactoryBean() {
        FormattingConversionServiceFactoryBean formattingConversionServiceFactoryBean = new FormattingConversionServiceFactoryBean();
        formattingConversionServiceFactoryBean.setFormatters(Set.of(rangedValuesFormatter));
        return formattingConversionServiceFactoryBean;
    }
}
