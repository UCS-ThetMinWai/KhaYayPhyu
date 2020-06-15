package com.khayayphyu.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.khayayphyu.dao.PriceDao;
import com.khayayphyu.domain.SalePrice;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
@Repository
public class PriceDaoImpl extends AbstractDaoImpl<SalePrice, Serializable> implements PriceDao {
	protected PriceDaoImpl() {
		super(SalePrice.class);
	}

	public void save(SalePrice price) throws ServiceUnavailableException {
		saveOrUpdate(price);
	}
}
