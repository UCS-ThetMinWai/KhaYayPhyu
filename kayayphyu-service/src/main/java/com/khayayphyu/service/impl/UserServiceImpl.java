package com.khayayphyu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.UserDao;
import com.khayayphyu.domain.User;
import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.UserService;

@Transactional(readOnly = true)
@Service("userService")
public class UserServiceImpl extends AbstractServiceImpl<User> implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public List<User> findByName(String name) throws ServiceUnavailableException {
		String queryStr = "from User user where user.name=:dataInput";
		List<User> userList = userDao.findByString(queryStr, name);
		return userList;
	}
	
	@Override
	public List<User> findByBoId(String boId) throws ServiceUnavailableException {
		String queryStr = "from User user where user.boId=:dataInput";
		List<User> userList = userDao.findByString(queryStr, boId);
		return userList;
	}

	@Transactional(readOnly = false)
	@Override
	public void saveUser(User user) throws ServiceUnavailableException {
		if(user.isBoIdRequired()) {
			user.setBoId(getNextBoId(EntityType.USER));
		}
		userDao.save(user);
	}

	@Override
	public long getCount() {
		return userDao.getCount("select count(user) from User user");
	}

}
