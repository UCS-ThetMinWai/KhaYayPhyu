package com.khayayphyu.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.khayayphyu.domain.User;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface UserServiceResource {
	Boolean createUser(HttpServletRequest request, User user);
	boolean deleteUser(String boId) throws ServiceUnavailableException;
	User findByBoId(HttpServletRequest request, String boId)throws ServiceUnavailableException;
	List<User> findByName(HttpServletRequest request, String name)throws ServiceUnavailableException;
	List<User> getAllUser(HttpServletRequest request)throws ServiceUnavailableException;
}
