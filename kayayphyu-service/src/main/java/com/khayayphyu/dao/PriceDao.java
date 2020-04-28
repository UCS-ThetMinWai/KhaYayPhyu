package com.khayayphyu.dao;

import java.io.Serializable;

import com.khayayphyu.domain.Price;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface PriceDao extends AbstractDao<Price, Serializable> {
	public void save(Price price)throws ServiceUnavailableException;
}
