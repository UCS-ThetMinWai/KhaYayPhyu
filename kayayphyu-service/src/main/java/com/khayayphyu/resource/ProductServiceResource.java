package com.khayayphyu.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface ProductServiceResource {
	boolean create(Product product);

	boolean deleteProduct(String boId) throws ServiceUnavailableException;

	Product findByProductBoId(HttpServletRequest request, String boId) throws ServiceUnavailableException;

	// List<Product> findByProductName(HttpServletRequest request, String
	// name)throws ServiceUnavailableException;
	List<Product> getAllProduct(HttpServletRequest request) throws ServiceUnavailableException;
}
