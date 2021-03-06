package com.khayayphyu.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.khayayphyu.dao.PurchasePriceDao;
import com.khayayphyu.domain.PurchasePrice;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

@Component
public class PurchasePriceDaoImpl extends AbstractDaoImpl<PurchasePrice, Serializable> implements PurchasePriceDao {

	@Override
	public void save(PurchasePrice purchasePrice) throws ServiceUnavailableException {
		saveOrUpdate(purchasePrice);
	}

}
