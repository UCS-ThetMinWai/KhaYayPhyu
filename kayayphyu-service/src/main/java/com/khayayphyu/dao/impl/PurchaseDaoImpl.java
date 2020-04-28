package com.khayayphyu.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.khayayphyu.dao.PurchaseDao;
import com.khayayphyu.domain.Purchase;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
@Repository
public class PurchaseDaoImpl extends AbstractDaoImpl<Purchase, Serializable> implements PurchaseDao {
	protected PurchaseDaoImpl() {
		super(Purchase.class);
	}

	public void save(Purchase purchase) throws ServiceUnavailableException {
		saveOrUpdate(purchase);
	}
}
