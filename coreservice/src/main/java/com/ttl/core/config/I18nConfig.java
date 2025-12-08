package com.ttl.core.config;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import jakarta.validation.MessageInterpolator;
import jakarta.validation.Validator;

@Configuration
public class I18nConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(Locale.forLanguageTag("vi"));
        return messageSource;
    }
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setDefaultLocale(Locale.forLanguageTag("vi"));
        return resolver;
    }
    
    @Bean
    public Validator validator(MessageSource messageSource) {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();

        factory.setMessageInterpolator(new MessageInterpolator() {
            @Override
            public String interpolate(String messageTemplate, Context context) {
                return messageTemplate; // Trả về CODE (E101, E102...)
            }

            @Override
            public String interpolate(String messageTemplate, Context context, Locale locale) {
                return messageTemplate; // Trả về CODE
            }
        });

        factory.setValidationMessageSource(messageSource);
        return factory;
    }

}
