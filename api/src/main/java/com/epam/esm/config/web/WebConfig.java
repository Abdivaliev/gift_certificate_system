package com.epam.esm.config.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * Configuration class for setting up the web application.
 * This class extends WebMvcConfigurationSupport and sets up the message converters and content negotiation.
 *
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
@Configuration
@ComponentScan(basePackages = "com.epam.esm")
public class WebConfig extends WebMvcConfigurationSupport {
    /**
     * Configures the message converters.
     * It adds the MappingJackson2HttpMessageConverter to the list of converters.
     *
     * @param converters The list of message converters to configure.
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converters.add(converter);
    }

    /**
     * Configures the content negotiation.
     * It sets the default content type to APPLICATION_JSON.
     *
     * @param configurer The content negotiation configurer to configure.
     */
    @Override
    protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
    }
}
