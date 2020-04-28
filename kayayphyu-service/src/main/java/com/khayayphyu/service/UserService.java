package com.khayayphyu.service;

import com.khayayphyu.domain.User;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface UserService extends AbstractService<User> {
	public void saveUser(User user)throws ServiceUnavailableException;
}
