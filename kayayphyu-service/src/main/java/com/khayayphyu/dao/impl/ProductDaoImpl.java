package com.khayayphyu.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.khayayphyu.dao.ProductDao;
import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
@Repository
public class ProductDaoImpl extends AbstractDaoImpl<Product, Serializable> implements ProductDao {
	protected ProductDaoImpl() {
		super(Product.class);
	}

	public void save(Product product) throws ServiceUnavailableException {
		saveOrUpdate(product);
	}
}
