package fr.warlog;

import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;

/**
 * old-school servlet initializer.
 * @author philippe
 *
 */
public class FooInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext container) {
        WebApplicationContext appContext = new ServletWebServerApplicationContext();
        ServletRegistration.Dynamic dispatcher =
           container.addServlet("dispatcher", new DispatcherServlet(appContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
 }