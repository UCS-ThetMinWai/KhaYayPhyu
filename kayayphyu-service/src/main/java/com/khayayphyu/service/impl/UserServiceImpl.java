package com.khayayphyu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.UserDao;
import com.khayayphyu.domain.User;
import com.khayayphyu.domain.constant.Status;
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
		String queryStr = "from User user where user.name like :dataInput and user.status != :status";
		List<User> userList = userDao.findByString(queryStr, "%" + name + "%");
		return userList;
	}

	@Override
	public User findByBoId(String boId) throws ServiceUnavailableException {
		String queryStr = "from User user where user.boId = :dataInput and user.status != :status";
		List<User> userList = userDao.findByString(queryStr, boId);
		return userList.get(0);
	}

	@Transactional(readOnly = false)
	@Override
	public void saveUser(User user) throws ServiceUnavailableException {
		if (user.isBoIdRequired()) {
			user.setBoId(getNextBoId(EntityType.USER));
			user.setStatus(Status.ACTIVE);
		}
		userDao.save(user);
	}
	
	@Transactional(readOnly = false)
	@Override
	public void deleteUser(User user) throws ServiceUnavailableException {
		user.setStatus(Status.DELETED);
		saveUser(user);
	}

	@Override
	public long getCount() {
		return userDao.getCount("select count(user) from User user");
	}

	@Override
	public List<User> getAllUser() throws ServiceUnavailableException {
		List<User> userList = userDao.getAll("From User user and user.status != :status");
		return userList;
	}

}
