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
 */
@Configuration
public class LanguageConfig extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {
    List<Locale> LOCALES = Arrays.asList(
            new Locale("en"),
            new Locale("ru"));

    @Override
    @NonNull
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader("Accept-Language");
        return headerLang == null || headerLang.isEmpty()
                ? Locale.getDefault()
                : Locale.lookup(Locale.LanguageRange.parse(headerLang), LOCALES);
    }


    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename("messages");
        rs.setDefaultEncoding("UTF-8");
        rs.setUseCodeAsDefaultMessage(true);
        return rs;
    }
}
