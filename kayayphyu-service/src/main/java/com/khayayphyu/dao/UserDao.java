package com.khayayphyu.dao;

import java.io.Serializable;

import com.khayayphyu.domain.User;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface UserDao extends AbstractDao<User, Serializable> {
	public void save(User user)throws ServiceUnavailableException;
}
