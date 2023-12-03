package com.epam.esm.config.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Component class for translating messages based on the locale.
 * This class uses the ResourceBundleMessageSource to get the messages.
 *
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */

@Component
public class Translator {
    private static ResourceBundleMessageSource messageSource;

    /**
     * Constructor for the Translator class.
     * It sets the ResourceBundleMessageSource.
     *
     * @param messageSource The ResourceBundleMessageSource to set.
     */
    @Autowired
    Translator(ResourceBundleMessageSource messageSource) {
        Translator.messageSource = messageSource;
    }

    /**
     * Translates the given message based on the locale from the LocaleContextHolder.
     *
     * @param msg The message to translate.
     * @return The translated message.
     */
    public static String toLocale(String msg) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msg, null, locale);
    }
}
