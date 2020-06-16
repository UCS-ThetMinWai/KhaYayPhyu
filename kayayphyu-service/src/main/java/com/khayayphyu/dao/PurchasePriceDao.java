package com.khayayphyu.dao;

import java.io.Serializable;

import com.khayayphyu.domain.PurchasePrice;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface PurchasePriceDao extends AbstractDao<PurchasePrice, Serializable> {
	public void save(PurchasePrice purchasePrice) throws ServiceUnavailableException;
}
