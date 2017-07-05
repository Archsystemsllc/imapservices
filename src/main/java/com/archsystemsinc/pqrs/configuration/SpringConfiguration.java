package com.archsystemsinc.pqrs.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.archsystemsinc.pqrs")
public class SpringConfiguration extends WebMvcConfigurerAdapter{
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		String activeProfile = System.getProperty("spring.profiles.active",
				"local");
		String propertiesFilename = "application-" + activeProfile
				+ ".properties";
		System.out.println("propertiesFilename:" + propertiesFilename);
		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		configurer.setLocation(new ClassPathResource(propertiesFilename));

		return configurer;
	}


}