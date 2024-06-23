package com.mitocode;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * 
 * Para establecer las llaves de nombre.size, apellidos.size en los archivos properties de idiomas
 * Para referenciar que archivo de idioma se utilizara, seleccionado desde el UtilController.
 * Java i18n Internacionalizacion
 * 
 */

@Configuration
public class MessagesConfig {

    // Carga los properties
    @Bean
    MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

    // Establece x default un locale
    @Bean
    LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.ROOT);
		return slr;
	}

    // Para resolver las variables en messages
    @Bean
    LocalValidatorFactoryBean getValidator() {
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setValidationMessageSource(messageSource());
		return bean;
	}

}
