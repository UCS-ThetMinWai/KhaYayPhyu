package com.khayayphyu.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.khayayphyu.dao.UserDao;
import com.khayayphyu.domain.User;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
@Repository
public class UserDaoImpl extends AbstractDaoImpl<User, Serializable> implements UserDao {
	
	public void save(User user) throws ServiceUnavailableException {
		saveOrUpdate(user);
	}
	
}
