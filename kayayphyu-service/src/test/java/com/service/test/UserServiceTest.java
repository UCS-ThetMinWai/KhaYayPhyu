package com.service.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.khayayphyu.domain.User;
import com.khayayphyu.domain.constant.Role;
import com.khayayphyu.domain.constant.Status;
import com.khayayphyu.domain.constant.SystemConstant;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.UserService;

public class UserServiceTest extends ServiceTest {
	private Logger logger = Logger.getLogger(UserServiceTest.class);
	
	@Autowired
	private UserService userService;
	
	@Test
	public void testSaveUser() {
		User user = new User();
		user.setBoId(SystemConstant.BOID_REQUIRED);
		user.setName("U mg");
		user.setAge(27);
		user.setStatus(Status.DELETED);
		user.setAddress("Kyun Ywar");
		user.setPhoneNumber("09771443284");
		user.setRole(Role.EMPLOYEE);
		
		try {
			userService.saveUser(user);
			logger.info("saved!");
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
	}
	
}
