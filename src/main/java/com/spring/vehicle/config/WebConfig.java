package com.spring.vehicle.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig {

	@Value("${cors.allowed-origins}")
	List<String> allowedOrigins;

	@Bean
	@ConditionalOnProperty(value = "cors.allowed-origins")
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins(allowedOrigins.toArray(new String[] {})).allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("Authorization", "Content-type");
			}
		};
	}
}