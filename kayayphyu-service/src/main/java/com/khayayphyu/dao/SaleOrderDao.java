package com.khayayphyu.dao;

import java.io.Serializable;

import com.khayayphyu.domain.SaleOrder;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface SaleOrderDao extends AbstractDao<SaleOrder, Serializable> {
	public void save(SaleOrder saleOrder)throws ServiceUnavailableException;
}
