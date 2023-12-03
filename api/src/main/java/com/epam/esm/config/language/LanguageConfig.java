package com.epam.esm.config.language;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Configuration class for setting up language support.
 * This class extends AcceptHeaderLocaleResolver and implements WebMvcConfigurer.
 * It sets up the locales and message source for internationalization (i18n).
 *
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
@Configuration
public class LanguageConfig extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {
    /**
     * List of supported locales.
     */
    List<Locale> LOCALES = Arrays.asList(
            new Locale("en"),
            new Locale("ru"));

    /**
     * Resolves the locale from the 'Accept-Language' header of the HTTP request.
     * If the header is null or empty, it returns the default locale.
     *
     * @param request The HTTP request to resolve the locale from.
     * @return The resolved locale.
     */
    @Override
    @NonNull
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader("Accept-Language");
        return headerLang == null || headerLang.isEmpty()
                ? Locale.getDefault()
                : Locale.lookup(Locale.LanguageRange.parse(headerLang), LOCALES);
    }

    /**
     * Sets up the ResourceBundleMessageSource with the base name 'messages',
     * default encoding 'UTF-8', and uses code as default message.
     *
     * @return The set-up ResourceBundleMessageSource.
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename("messages");
        rs.setDefaultEncoding("UTF-8");
        rs.setUseCodeAsDefaultMessage(true);
        return rs;
    }
}
