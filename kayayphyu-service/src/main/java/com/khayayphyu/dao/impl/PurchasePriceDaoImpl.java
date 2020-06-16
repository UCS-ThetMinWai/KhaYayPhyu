package com.khayayphyu.dao.impl;

import java.io.Serializable;

import com.khayayphyu.dao.PurchasePriceDao;
import com.khayayphyu.domain.PurchasePrice;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public class PurchasePriceDaoImpl extends AbstractDaoImpl<PurchasePrice, Serializable> implements PurchasePriceDao {

	protected PurchasePriceDaoImpl() {
		super(PurchasePrice.class);
		
	}

	@Override
	public void save(PurchasePrice purchasePrice) throws ServiceUnavailableException {
		saveOrUpdate(purchasePrice);
	}
	

}
