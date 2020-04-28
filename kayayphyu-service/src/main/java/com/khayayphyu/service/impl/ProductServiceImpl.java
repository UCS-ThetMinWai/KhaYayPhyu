package com.khayayphyu.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.ProductDao;
import com.khayayphyu.domain.Price;
import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.RawProduct;
import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.PriceService;
import com.khayayphyu.service.ProductService;
import com.khayayphyu.service.RawProductService;

@Service("productService")
@Transactional(readOnly = true)
public class ProductServiceImpl extends AbstractServiceImpl<Product> implements ProductService {
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private PriceService priceService;
	
	@Autowired
	private RawProductService rawProductService;
	
	public void ensuredProductBoId(Product product) {
		if(product.getPriceList() == null || product.getRawProduct() == null)
			return;
		if(CollectionUtils.isEmpty(product.getPriceList())) 
			return;
		for (Price price : product.getPriceList()) {
			if (price.isBoIdRequired()) {
				price.setBoId(priceService.getNextBoId(EntityType.PRICE));
			}
		}
		
		RawProduct rawProduct = product.getRawProduct();
		if(rawProduct != null) {
			rawProduct.setBoId(rawProductService.getNextBoId(EntityType.RAWPRODUCT));
		}
	}

	@Transactional(readOnly = false)
	@Override
	public void saveProduct(Product product) throws ServiceUnavailableException {
		if(product.isBoIdRequired()) {
			product.setBoId(getNextBoId(EntityType.PRODUCT));
			ensuredProductBoId(product);
		}
		productDao.save(product);
	}

	@Override
	public long getCount() {
		return productDao.getCount("select count(product) from Product product");
	}
	
	@Override
	public List<Product> findByName(String name) throws ServiceUnavailableException {
		String queryStr = "select product from Product product where product.productName=:dataInput";
		List<Product> productList = productDao.findByString(queryStr, name);
		if (CollectionUtils.isEmpty(productList))
			return null;
		 hibernateInitializeProductList(productList);
		return productList;
	}
	
	@Override
	public List<Product> findByBoId(String boId) throws ServiceUnavailableException {
		String queryStr = "select product from Product product where product.boId=:dataInput";
		List<Product> productList = productDao.findByString(queryStr, boId);
		if (CollectionUtils.isEmpty(productList))
			return null;
		hibernateInitializeProductList(productList);
		return productList;
	}

	@Override
	public void hibernateInitializeProductList(List<Product> productList) {
		Hibernate.initialize(productList);
		if (CollectionUtils.isEmpty(productList))
			return;

		for (Product product : productList) {
			hibernateInitializeProduct(product);
		}
	}

	@Override
	public void hibernateInitializeProduct(Product product) {
		Hibernate.initialize(product);
		if(product == null)
			return;
		Hibernate.initialize(product.getRawProduct());
		for(Price price : product.getPriceList()) {
			Hibernate.initialize(price);
		}
	}

}
