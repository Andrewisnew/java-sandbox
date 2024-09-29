package edu.andrewisnew.java.spring.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

public class ViewPrioritisation {
    @Bean
    public ViewResolver xlsViewResolver() {
        BeanNameViewResolver resolver = new BeanNameViewResolver();
        resolver.setOrder(0); //lower first
        return resolver;
    }

    @Bean
    ViewResolver jspViewResolver() {
        InternalResourceViewResolver resolver =
                new InternalResourceViewResolver();
        resolver.setPrefix("/src/main/webapp/WEB-INF/");
        resolver.setSuffix(".jsp");
        resolver.setOrder(1);
        return resolver;
    }

    //http://localhost:8080/mvc-views/persons/list.xls
//    @Bean(name = "persons/list.xls")
//    public View excelView() {
//        return new PersonsExcelView();
//    }
}
