package com.service.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:hibernateContext.xml" })
@PropertySource({ "classpath:log4j.properties", "classpath:application.properties", "classpath:database.properties" })
public class ServiceTest {
	
	private static Logger logger = Logger.getLogger(ServiceTest.class);
	
	@Test
	public void AppTest() {
		logger.info("here");
	}
}
