package com.khayayphyu.resource.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.domain.jsonviews.SummaryView;
import com.khayayphyu.resource.ProductServiceResource;
import com.khayayphyu.service.ProductService;
import com.khayayphyu.service.impl.ProductServiceImpl;

@RestController
@RequestMapping(value = { "/product" })
public class ProductServiceResourceImpl extends AbstractServiceResourceImpl implements ProductServiceResource {

	private static Logger logger = Logger.getLogger(ProductServiceResourceImpl.class);

	@Autowired
	private ProductService productService;

	@RequestMapping(method = RequestMethod.POST, value = "")
	@Override
	public boolean create(@RequestBody Product product) {
		product.getItemList().forEach(item -> item.setParent(product));
		try {
			productService.saveProduct(product);
		} catch (ServiceUnavailableException e) {
			logger.error("Can't save product", e);
			return false;
		}
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{boId}")
	@Override
	public Product findByProductBoId(HttpServletRequest request, @PathVariable String boId) throws ServiceUnavailableException {
		return productService.findByBoId(boId, ProductServiceImpl::detailInitializer);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/sale/{boId}/{amount}")
	public Product updateSaleAmount(@PathVariable("boId") String productBoId, @PathVariable("amount") int saleAmount) {
		Product product = getDetailProduct(productBoId);
		productService.updateSaleAmount(product, saleAmount);
		return product;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/purchase/{boId}/{amount}")
	public Product updatePurchaseAmount(@PathVariable("boId") String productBoId, @PathVariable("amount") int purchaseAmount) {
		Product product = getDetailProduct(productBoId);
		productService.updatePurchaseAmount(product, purchaseAmount);
		return product;
	}

	@JsonView(SummaryView.class)
	@RequestMapping(method = RequestMethod.GET, value = "")
	@Override
	public List<Product> getAllProduct(HttpServletRequest request) throws ServiceUnavailableException {
		return productService.getAllProduct(ProductServiceImpl.productInitializer);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{boId}")
	@Override
	public boolean deleteProduct(@PathVariable("boId") String boId) throws ServiceUnavailableException {
		productService.deleteProduct(productService.findByBoId(boId));
		return true;
	}

	private Product getDetailProduct(String boId) {
		try {
			return productService.findByBoId(boId, ProductServiceImpl::detailInitializer);
		} catch (ServiceUnavailableException e) {
			return null;
		}
	}

}
