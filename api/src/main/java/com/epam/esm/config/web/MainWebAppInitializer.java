package com.epam.esm.config.web;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
/**
 * Main Web Application Initializer class for setting up the servlet context.
 * This class implements WebApplicationInitializer and sets up the DispatcherServlet.
 *
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
public class MainWebAppInitializer implements WebApplicationInitializer {
    /**
     * Sets up the servlet context on startup.
     * It registers the WebConfig class and sets up the DispatcherServlet.
     *
     * @param servletContext The servlet context to set up.
     */
    @Override
    public void onStartup(ServletContext servletContext) {

        AnnotationConfigWebApplicationContext root =
                new AnnotationConfigWebApplicationContext();

        root.register(WebConfig.class);

        ServletRegistration.Dynamic appServlet =
                servletContext.addServlet("mvc", new DispatcherServlet(root));
        appServlet.setLoadOnStartup(1);
        appServlet.addMapping("/");

    }
}
