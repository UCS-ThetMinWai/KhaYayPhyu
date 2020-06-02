package com.khayayphyu.service;

import java.util.List;

import com.khayayphyu.domain.User;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface UserService extends AbstractService<User> {
	public void saveUser(User user)throws ServiceUnavailableException;
	public void deleteUser(User user)throws ServiceUnavailableException;
	public List<User> getAllUser()throws ServiceUnavailableException;
}
