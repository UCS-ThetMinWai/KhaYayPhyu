package com.khayayphyu.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.khayayphyu.dao.PurchaseOrderDao;
import com.khayayphyu.domain.PurchaseOrder;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
@Repository
public class PurchaseOrderDaoImpl extends AbstractDaoImpl<PurchaseOrder, Serializable> implements PurchaseOrderDao {

	public void save(PurchaseOrder purchaseOrder) throws ServiceUnavailableException {
		saveOrUpdate(purchaseOrder);
	}
}
