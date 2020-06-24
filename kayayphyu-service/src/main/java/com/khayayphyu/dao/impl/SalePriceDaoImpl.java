package com.khayayphyu.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.khayayphyu.dao.SalePriceDao;
import com.khayayphyu.domain.SalePrice;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
@Repository
public class SalePriceDaoImpl extends AbstractDaoImpl<SalePrice, Serializable> implements SalePriceDao {
	
	public void save(SalePrice price) throws ServiceUnavailableException {
		saveOrUpdate(price);
	}
}
