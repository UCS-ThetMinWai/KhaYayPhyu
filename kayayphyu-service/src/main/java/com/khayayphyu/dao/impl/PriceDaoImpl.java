package com.khayayphyu.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.khayayphyu.dao.PriceDao;
import com.khayayphyu.domain.Price;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
@Repository
public class PriceDaoImpl extends AbstractDaoImpl<Price, Serializable> implements PriceDao {
	protected PriceDaoImpl() {
		super(Price.class);
	}

	public void save(Price price) throws ServiceUnavailableException {
		saveOrUpdate(price);
	}
}
