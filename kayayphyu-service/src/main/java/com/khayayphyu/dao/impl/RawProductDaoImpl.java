package com.khayayphyu.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.khayayphyu.dao.RawProductDao;
import com.khayayphyu.domain.RawProduct;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
@Repository
public class RawProductDaoImpl extends AbstractDaoImpl<RawProduct, Serializable> implements RawProductDao {
	protected RawProductDaoImpl() {
		super(RawProduct.class);
	}

	public void save(RawProduct rawProduct) throws ServiceUnavailableException {
		saveOrUpdate(rawProduct);
	}
}
