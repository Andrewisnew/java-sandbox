package edu.andrewisnew.java.spring.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ContentNegotiatingViewResolverConfiguration {
    @Bean
    ContentNegotiatingViewResolver viewResolver() {
        ContentNegotiationManagerFactoryBean factory
                = new ContentNegotiationManagerFactoryBean();
        factory.setDefaultContentType(MediaType.TEXT_HTML);
        factory.setIgnoreAcceptHeader(true);
        factory.setFavorParameter(false);
        factory.setFavorPathExtension(true);
        Properties properties = new Properties();
        properties.putAll(
                Map.of("html", MediaType.TEXT_HTML_VALUE,
                        "xls", "application/vnd.ms-excel",
                        "pdf", MediaType.APPLICATION_PDF_VALUE,
                        "json", MediaType.APPLICATION_JSON_VALUE)
        );
        factory.setMediaTypes(
                properties);
        ContentNegotiatingViewResolver resolver
                = new ContentNegotiatingViewResolver();
        resolver.setContentNegotiationManager(factory.getObject());
        resolver.setOrder(-1);
        resolver.setDefaultViews(defaultViewsList());
        resolver.setViewResolvers(resolverList());
        return resolver;
    }

    private List<View> defaultViewsList() {
        List<View> views = new ArrayList<>();
//        views.add(new PersonExcelView());
//        views.add(new PersonPdfView());
        views.add(new MappingJackson2JsonView());
        return views;
    }

    private List<ViewResolver> resolverList() {
        List<ViewResolver> resolvers = new ArrayList<>();
        resolvers.add(new BeanNameViewResolver());
//        resolvers.add(thymeViewResolver());
//        resolvers.add(new JsonViewResolver());
        return resolvers;
    }
}
