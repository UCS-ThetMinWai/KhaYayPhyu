package com.kayayphyu.applilcation.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ AppConfig.class })
@ComponentScan(basePackages = { "com.khayayphyu" })
public class CoreConfig {

}