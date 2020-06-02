package com.khayayphyu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
@Service("productService")
public interface ProductService extends AbstractService<Product> {
	public void saveProduct(Product product)throws ServiceUnavailableException;
	public void deleteProduct(Product product)throws ServiceUnavailableException;
	public List<Product> getAllProduct()throws ServiceUnavailableException;
	public void hibernateInitializeProductList(List<Product> productList);
	public void hibernateInitializeProduct(Product product);
}
