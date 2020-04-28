package com.khayayphyu.dao;

import java.io.Serializable;

import com.khayayphyu.domain.Sale;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface SaleDao extends AbstractDao<Sale, Serializable> {
	public void save(Sale sale)throws ServiceUnavailableException;
}
