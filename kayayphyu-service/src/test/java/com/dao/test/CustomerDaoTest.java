package com.dao.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.khayayphyu.dao.CustomerDao;
import com.khayayphyu.domain.Customer;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:hibernateContext.xml" })
@PropertySource({ "classpath:log4j.properties", "classpath:application.properties", "classpath:database.properties" })
public class CustomerDaoTest {
	
	@Autowired
	private CustomerDao customerDao;
	
	@Test
	public void saveTest() throws ServiceUnavailableException {
		Customer customer = new Customer();
		
		customerDao.save(customer);
	}
	
}
