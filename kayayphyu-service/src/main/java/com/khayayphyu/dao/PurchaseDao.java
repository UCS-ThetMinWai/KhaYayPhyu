package com.khayayphyu.dao;

import java.io.Serializable;

import com.khayayphyu.domain.Purchase;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface PurchaseDao extends AbstractDao<Purchase, Serializable> {
	public void save(Purchase purchase)throws ServiceUnavailableException;
}
