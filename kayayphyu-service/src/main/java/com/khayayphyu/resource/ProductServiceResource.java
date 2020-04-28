package com.khayayphyu.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface ProductServiceResource {
	Boolean createProduct(HttpServletRequest request, Product product);
	List<Product> findByProductBoId(HttpServletRequest request, String boId)throws ServiceUnavailableException;
	List<Product> findByProductName(HttpServletRequest request, String name)throws ServiceUnavailableException;
}
