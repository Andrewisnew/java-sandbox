//package edu.andrewisnew.java.spring.mvc.config;
//
//import jakarta.servlet.ServletContext;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRegistration;
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.servlet.DispatcherServlet;

////вместо web.xml
//public class WebInitializer implements WebApplicationInitializer {
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//        AnnotationConfigWebApplicationContext a;
//        ServletRegistration.Dynamic registration =
//                servletContext.addServlet("my-dispatcher", new DispatcherServlet());
//        registration.setLoadOnStartup(1);
//        registration.addMapping("/");
//        registration.setInitParameter("contextConfigLocation",
//                "edu.andrewisnew.java.spring.mvc.config.WebConfig");
//        registration.setInitParameter("contextClass",
//                "org.springframework.web.servlet.support.AnnotationConfigWebApplicationContext");
//    }
//}
