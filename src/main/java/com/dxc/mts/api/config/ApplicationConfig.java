package com.dxc.mts.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * 
 * @author mkhan339
 *
 */
@Configuration
@OpenAPIDefinition(info = @Info(title = "Money Transfer System"))
public class ApplicationConfig {

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("application");
		source.setBasename("message");
		source.setUseCodeAsDefaultMessage(true);
		return source;
	}
}
