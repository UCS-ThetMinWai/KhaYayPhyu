package com.khayayphyu.dao.impl;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.khayayphyu.dao.ItemDao;
import com.khayayphyu.domain.Item;

@Repository
public class ItemDaoImpl extends AbstractDaoImpl<Item, Serializable> implements ItemDao {

	private static Logger logger = Logger.getLogger(ItemDao.class);

	@Override
	public void delete(Item item) {
		logger.info("deletng :" + item.getId());
		Session session = getCurrentSession();
		session.delete(item);
		session.flush();
	}

}
