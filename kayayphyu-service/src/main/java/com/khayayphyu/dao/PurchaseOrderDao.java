package com.khayayphyu.dao;

import java.io.Serializable;

import com.khayayphyu.domain.PurchaseOrder;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface PurchaseOrderDao extends AbstractDao<PurchaseOrder, Serializable> {
	public void save(PurchaseOrder purchaseOrder)throws ServiceUnavailableException;
	public void delete(PurchaseOrder purchaseOrder) throws ServiceUnavailableException;
}
