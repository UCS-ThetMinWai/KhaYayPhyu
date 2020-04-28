package com.khayayphyu.dao;

import java.io.Serializable;

import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface ProductDao extends AbstractDao<Product, Serializable> {
	public void save(Product product)throws ServiceUnavailableException;
}
