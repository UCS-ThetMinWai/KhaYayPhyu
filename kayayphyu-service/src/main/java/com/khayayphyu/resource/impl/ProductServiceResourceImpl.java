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

	@RequestMapping(method = RequestMethod.GET, value = "boId/{boId}")
	@Override
	public List<Product> findByProductBoId(HttpServletRequest request,@PathVariable String boId) throws ServiceUnavailableException {
		return productService.findByBoId(boId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "name/{name}")
	@Override
	public List<Product> findByProductName(HttpServletRequest request,@PathVariable String name) throws ServiceUnavailableException {
		return productService.findByName(name);
	}

}
