package com.khayayphyu.resource.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.khayayphyu.domain.User;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.resource.UserServiceResource;
import com.khayayphyu.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserServiceResourceImpl extends AbstractServiceResourceImpl implements UserServiceResource {

	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.POST)
	@Override
	public Boolean createUser(HttpServletRequest request,@RequestBody User user) {
		try {
			userService.saveUser(user);
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}		
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{boId}")
	@Override
	public User findByBoId(HttpServletRequest request,@PathVariable String boId) throws ServiceUnavailableException {
		return userService.findByBoId(boId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search/{name}")
	@Override
	public List<User> findByName(HttpServletRequest request,@PathVariable String name) throws ServiceUnavailableException {
		return userService.findByName(name);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{boId}")
	@Override
	public boolean deleteUser(@PathVariable("boId") String boId) throws ServiceUnavailableException {
		userService.deleteUser(userService.findByBoId(boId));
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "")
	@Override
	public List<User> getAllUser(HttpServletRequest request) throws ServiceUnavailableException {
		return userService.getAllUser();
	}

}
