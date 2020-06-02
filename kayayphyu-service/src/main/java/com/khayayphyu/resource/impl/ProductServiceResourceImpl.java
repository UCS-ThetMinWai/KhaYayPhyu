package com.khayayphyu.resource.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.resource.ProductServiceResource;
import com.khayayphyu.service.ProductService;

@RestController
@RequestMapping(value = {"/product"})
public class ProductServiceResourceImpl extends AbstractServiceResourceImpl implements ProductServiceResource {
	
	@Autowired
	private ProductService productService;

	@RequestMapping(method = RequestMethod.POST, value = "")
	@Override
	public Boolean createProduct(HttpServletRequest request,@RequestBody Product product) {
		try {
			productService.saveProduct(product);
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{boId}")
	@Override
	public Product findByProductBoId(HttpServletRequest request,@PathVariable String boId) throws ServiceUnavailableException {
		return productService.findByBoId(boId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search/{name}")
	@Override
	public List<Product> findByProductName(HttpServletRequest request,@PathVariable String name) throws ServiceUnavailableException {
		return productService.findByName(name);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	@Override
	public List<Product> getAllProduct(HttpServletRequest request) throws ServiceUnavailableException {
		return productService.getAllProduct();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{boId}")
	@Override
	public boolean deleteProduct(String boId) throws ServiceUnavailableException {
		productService.deleteProduct(productService.findByBoId(boId));
		return true;
	}

}
