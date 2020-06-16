package com.khayayphyu.dao;

import java.io.Serializable;

import com.khayayphyu.domain.SalePrice;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface SalePriceDao extends AbstractDao<SalePrice, Serializable> {
	public void save(SalePrice price)throws ServiceUnavailableException;
}
