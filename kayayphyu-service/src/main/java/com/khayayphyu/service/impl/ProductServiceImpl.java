package com.khayayphyu.service.impl;

import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.ProductDao;
import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.PurchasePrice;
import com.khayayphyu.domain.SalePrice;
import com.khayayphyu.domain.constant.Status;
import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.ProductService;
import com.khayayphyu.service.PurchasePriceService;
import com.khayayphyu.service.SalePriceService;

@Service("productService")
@Transactional(readOnly = true)
public class ProductServiceImpl extends AbstractServiceImpl<Product> implements ProductService {
	// private Logger logger = Logger.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductDao productDao;

	@Autowired
	private SalePriceService priceService;

	@Autowired
	private PurchasePriceService purchasePriceService;

	private static Logger logger = Logger.getLogger(ProductServiceImpl.class);

	public static final Consumer<Product> productInitializer = product -> {
		if (product == null)
			return;
		Hibernate.initialize(product.getPurchasePrice());
		Hibernate.initialize(product.getSalePrice());
		product.setSalePriceHistory(null);
		product.setPurchasePriceHistory(null);
		product.setProductList(null);
	};

	public static final Consumer<Product> detailInitializer = product -> {
		if (product == null)
			return;
		Hibernate.initialize(product.getPurchasePrice());
		Hibernate.initialize(product.getSalePrice());
		Hibernate.initialize(product.getPurchasePriceHistory());
		Hibernate.initialize(product.getSalePriceHistory());
		Hibernate.initialize(product.getProductList());
	};

	public void ensuredProductBoId(Product product) {
		if (product.getSalePriceHistory() == null || product.getPurchasePriceHistory() == null)
			return;
		if (CollectionUtils.isEmpty(product.getPurchasePriceHistory())
				|| CollectionUtils.isEmpty(product.getSalePriceHistory()))
			return;
		for (SalePrice price : product.getSalePriceHistory()) {
			if (price.isBoIdRequired()) {
				price.setBoId(priceService.getNextBoId(EntityType.PRICE));
			}
		}
		for (PurchasePrice purchasePrice : product.getPurchasePriceHistory()) {
			if (purchasePrice.isNew()) {
				purchasePrice.setBoId(priceService.getNextBoId(EntityType.PRICE));
			}
		}
	}

	@Override
	@Transactional(readOnly = false)
	public boolean updateSaleAmount(Product product, int amount) {
		if (product == null || amount < 0)
			return false;
		SalePrice price = SalePrice.create(amount);
		try {
			priceService.savePrice(price);
			product.addNewSalePrice(price);
			productDao.saveOrUpdate(product);
		} catch (ServiceUnavailableException e) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional(readOnly = false)
	public boolean updatePurchaseAmount(Product product, int amount) {
		if (product == null || amount < 0) {
			return false;
		}
		PurchasePrice purchasePrice = PurchasePrice.create(amount);
		try {
			purchasePriceService.savePrice(purchasePrice);
			product.addNewPurchasePrice(purchasePrice);
			productDao.saveOrUpdate(product);

		} catch (ServiceUnavailableException e) {
			return false;
		}
		return true;
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

	@Transactional(readOnly = false)
	public boolean save(List<Product> productList) {
		for (Product product : productList) {
			try {
				saveExistingProduct(product);
			} catch (ServiceUnavailableException e) {
				logger.error("Can't save product :" + product.getBoId());
				return false;
			}
		}
		return true;
	}

	private void saveExistingProduct(Product product) throws ServiceUnavailableException {
		productDao.saveOrUpdate(product);
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
		return findByBoId(boId, this::hibernateInitializeProduct);
	}

	public Product findByBoId(String boId, Consumer<Product> productInitalizer) {
		String queryStr = "from Product product where product.boId=:dataInput and product.status != :status";
		List<Product> productList = productDao.findByString(queryStr, boId);
		if (CollectionUtils.isEmpty(productList))
			return null;
		Product product = productList.get(0);
		productInitalizer.accept(product);
		return product;
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
		for (SalePrice price : product.getSalePriceHistory()) {
			Hibernate.initialize(price);
		}
		for (PurchasePrice purchasePrice : product.getPurchasePriceHistory()) {
			Hibernate.initialize(purchasePrice);
		}
	}

	@Override
	public List<Product> getAllProduct() throws ServiceUnavailableException {
		return getAllProduct(this::hibernateInitializeProduct);
	}

	public List<Product> getAllProduct(Consumer<Product> productInitializer) throws ServiceUnavailableException {
		List<Product> productList = getRawAllProduct();
		productList.forEach(productInitializer);
		return productList;
	}

	private List<Product> getRawAllProduct() {
		return productDao.getActiveObjects("from Product product where product.status != :status");
	}

}
