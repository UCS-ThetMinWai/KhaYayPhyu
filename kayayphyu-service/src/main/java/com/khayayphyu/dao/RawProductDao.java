package com.khayayphyu.dao;

import java.io.Serializable;

import com.khayayphyu.domain.RawProduct;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface RawProductDao extends AbstractDao<RawProduct, Serializable> {
	public void save(RawProduct rawProduct)throws ServiceUnavailableException;
}
