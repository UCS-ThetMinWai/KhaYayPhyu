package com.kayayphyu.applilcation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Import({ RepositoryConfig.class })
@EnableScheduling
@EnableAsync
@EnableAspectJAutoProxy
@PropertySource({"classpath:database.properties","classpath:application.properties"})
public class AppConfig{
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
//	@Bean
//	public CommonsMultipartResolver multipartResolver(){
//		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
//	    commonsMultipartResolver.setDefaultEncoding("utf-8");
//	    commonsMultipartResolver.setMaxUploadSize(10000000);// 10MB
//	    return commonsMultipartResolver;
//	}
	
//	@Bean(name = "projectConfig")
//	public PropertyPlaceholderConfigurer getProjectConfig() {
//		PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
//		ppc.setFileEncoding("UTF-8");
//		ppc.setLocation(new ClassPathResource("project.properties"));
//		ppc.setIgnoreUnresolvablePlaceholders(true);
//		return ppc;
//	}	
}