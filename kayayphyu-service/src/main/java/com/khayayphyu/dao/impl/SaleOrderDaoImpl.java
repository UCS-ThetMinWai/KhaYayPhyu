package com.khayayphyu.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.khayayphyu.dao.SaleOrderDao;
import com.khayayphyu.domain.SaleOrder;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
@Repository
public class SaleOrderDaoImpl extends AbstractDaoImpl<SaleOrder, Serializable> implements SaleOrderDao {
	
	public void save(SaleOrder saleOrder) throws ServiceUnavailableException {
		saveOrUpdate(saleOrder);
	}
	
}
