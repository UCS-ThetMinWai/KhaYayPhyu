package com.khayayphyu.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.khayayphyu.domain.User;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface UserServiceResource {
	Boolean createUsere(HttpServletRequest request, User user);
	List<User> findByBoId(HttpServletRequest request, String boId)throws ServiceUnavailableException;
	List<User> findByName(HttpServletRequest request, String name)throws ServiceUnavailableException;
}
