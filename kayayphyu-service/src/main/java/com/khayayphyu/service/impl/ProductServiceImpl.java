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
import com.khayayphyu.domain.constant.Status;
import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.PriceService;
import com.khayayphyu.service.ProductService;

@Service("productService")
@Transactional(readOnly = true)
public class ProductServiceImpl extends AbstractServiceImpl<Product> implements ProductService {
	//private Logger logger = Logger.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductDao productDao;

	@Autowired
	private PriceService priceService;

	public void ensuredProductBoId(Product product) {
		if (product.getPriceList() == null)
			return;
		if (CollectionUtils.isEmpty(product.getPriceList()))
			return;
		for (Price price : product.getPriceList()) {
			if (price.isBoIdRequired()) {
				price.setBoId(priceService.getNextBoId(EntityType.PRICE));
			}
		}

	}

	@Transactional(readOnly = false)
	@Override
	public void saveProduct(Product product) throws ServiceUnavailableException {
		if (product.isNew()) {
			saveNewProduct(product);
		} else {
			saveExistingProduct(product);
		}
	}

	private void saveExistingProduct(Product product) throws ServiceUnavailableException {
		Product oldProduct = findByBoId(product.getBoId());
		productDao.clearSession();
		if (product.isSamePrice(oldProduct)) {
			productDao.saveOrUpdate(product);
		} else {
			// product.setBoId(getNextBoId(EntityType.PRODUCT));
			
			Price currentPrice = product.getCurrentPrice();
			String currentPriceBoId = currentPrice.getBoId();
			currentPrice.setId(0);
			currentPrice.setBoId(priceService.getNextBoId(EntityType.PRICE));
			
			Price oldPrice = oldProduct.getCurrentPrice();
			oldPrice.setProduct(product);
			oldPrice.setBoId(currentPriceBoId);
			productDao.saveOrUpdate(product);
		}
	}

	private void saveNewProduct(Product product) throws ServiceUnavailableException {
		product.setBoId(getNextBoId(EntityType.PRODUCT));
		product.setStatus(Status.OPEN);
		ensuredProductBoId(product);
		productDao.save(product);
	}

	@Override
	public long getCount() {
		return productDao.getCount("select count(product) from Product product");
	}

	@Override
	public List<Product> findByName(String name) throws ServiceUnavailableException {
		String queryStr = "from Product product where product.productName like :dataInput and product.status != :status";
		List<Product> productList = productDao.findByString(queryStr, "%" + name + "%");
		if (CollectionUtils.isEmpty(productList))
			return null;
		hibernateInitializeProductList(productList);
		return productList;
	}

	@Override
	public Product findByBoId(String boId) throws ServiceUnavailableException {
		String queryStr = "select product from Product product where product.boId=:dataInput and product.status != :status";
		List<Product> productList = productDao.findByString(queryStr, boId);
		if (CollectionUtils.isEmpty(productList))
			return null;
		hibernateInitializeProductList(productList);
		return productList.get(0);
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

	@Transactional(readOnly = false)
	@Override
	public void deleteProduct(Product product) throws ServiceUnavailableException {
		product.setStatus(Status.DELETED);
		productDao.saveOrUpdate(product);
	}

	@Override
	public void hibernateInitializeProduct(Product product) {
		Hibernate.initialize(product);
		if (product == null)
			return;
		for (Price price : product.getPriceList()) {
			Hibernate.initialize(price);
		}
	}

	@Override
	public List<Product> getAllProduct() throws ServiceUnavailableException {
		List<Product> productList = productDao.getActiveObjects("from Product product where product.status != :status");
		hibernateInitializeProductList(productList);
		return productList;
	}

}
