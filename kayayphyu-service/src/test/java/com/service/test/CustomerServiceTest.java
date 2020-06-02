package com.service.test;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.khayayphyu.domain.Customer;
import com.khayayphyu.domain.constant.Status;
import com.khayayphyu.domain.constant.SystemConstant;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.CustomerService;

public class CustomerServiceTest extends ServiceTest {
	Logger logger = Logger.getLogger(CustomerServiceTest.class);
	
	@Autowired
	private CustomerService customerService;
	
	@Test
	public void testSaveCustomer() {
		Customer customer = new Customer();
		customer.setBoId(SystemConstant.BOID_REQUIRED);
		customer.setName("Myo Thura");
		customer.setAge(27);
		customer.setStatus(Status.ACTIVE);
		customer.setAddress("Kyun Ywar");
		customer.setPhoneNumber("09684876787");
		try {
			customerService.saveCustomer(customer);
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFindByCustomerName() {
		try {
			List<Customer> customerList = customerService.findByName("Myo Thura");
			for(Customer customer : customerList) {
	
				System.out.println(customer.getBoId()+" "+customer.getName() + " " + customer.getAge());
			}

		} catch (ServiceUnavailableException e) {
			logger.info("Error is:::" + e);

		}
	}
	
	@Test
	public void testFindByBoId() {
		try {
			Customer customerList = customerService.findByBoId("CUSTOMER00000002");
			logger.info(customerList);

		} catch (ServiceUnavailableException e) {
			logger.info("Error is:::" + e);

		}

	}
}
