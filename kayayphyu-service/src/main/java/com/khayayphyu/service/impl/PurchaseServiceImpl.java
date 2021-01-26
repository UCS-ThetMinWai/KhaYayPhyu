package com.khayayphyu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.PurchaseDao;
import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.Purchase;
import com.khayayphyu.domain.PurchaseOrder;
import com.khayayphyu.domain.constant.Status;
import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.CustomerService;
import com.khayayphyu.service.ProductService;
import com.khayayphyu.service.PurchaseOrderService;
import com.khayayphyu.service.PurchaseService;

@Transactional(readOnly = true)
@Service("purchaseService")
public class PurchaseServiceImpl extends AbstractServiceImpl<Purchase> implements PurchaseService {
	@Autowired
	private CustomerService customerService;

	@Autowired
	private PurchaseOrderService purchaseOrderService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private PurchaseDao purchaseDao;

	public static void detailInitializer(Purchase purchase) {
		Hibernate.initialize(purchase);
		Hibernate.initialize(purchase.getUser());
		Hibernate.initialize(purchase.getCustomer());
		for (PurchaseOrder purchaseOrder : purchase.getPurchaseOrderList()) {
			Hibernate.initialize(purchaseOrder);
			Hibernate.initialize(purchaseOrder.getProduct());
		}
	}

	@Transactional(readOnly = false)
	public boolean syncWithDb(Purchase purchase) {
		List<Product> productList = new ArrayList<>();
		for (PurchaseOrder purchaseOrder : purchase.getPurchaseOrderList()) {
			Product product = purchaseOrder.getProduct();
			try {
				product = productService.findByBoId(product.getBoId());
			} catch (ServiceUnavailableException e) {

			}
			product.setQuantity(product.getQuantity() + purchaseOrder.getQuantity());
			productList.add(product);
		}
		return productService.save(productList);
	}

	@Override
	public List<Purchase> getAllPurchase() throws ServiceUnavailableException {
		List<Purchase> purchaseList = purchaseDao.getAll("From Purchase purchase");
		hibernateInitializePurchaseList(purchaseList);
		return purchaseList;
	}

	@Override
	@Transactional(readOnly = false)
	public void savePurchase(Purchase purchase) throws ServiceUnavailableException {
		if (purchase.isNew()) {
			purchase.setBoId(getNextBoId(EntityType.PURCHASE));
			purchase.setStatus(Status.OPEN);
		}
		purchase.setStatus(purchase.getBalance() > 0 ? Status.OPEN : Status.CLOSE);
		purchaseDao.save(purchase);
	}

	@Override
	public long getCount() {
		return purchaseDao.getCount("select count(purchase) from Purchase purchase");
	}

	@Override
	public List<Purchase> findByPeriod(Date startDate, Date endDate) throws ServiceUnavailableException {
		String queryString = "from Purchase purchase where purchase.purchaseDate between :dataInput and :dataInput1";
		List<Purchase> purchaseList = purchaseDao.findByDate(queryString, startDate, endDate);
		return purchaseList;
	}

	@Override
	public void hibernateInitializePurchaseList(List<Purchase> purchaseList) {
		purchaseList.forEach(this::hibernateInitializePurchase);
	}

	@Override
	public void hibernateInitializePurchase(Purchase purchase) {
		Hibernate.initialize(purchase);
		if (purchase == null)
			return;
		customerService.hibernateInitializeCustomer(purchase.getCustomer());
		Hibernate.initialize(purchase.getUser());

		for (PurchaseOrder purchaseOrder : purchase.getPurchaseOrderList()) {
			purchaseOrderService.hibernateInitializePurchaseOrder(purchaseOrder);
		}
	}

	@Transactional(readOnly = false)
	@Override
	public void deletePurchase(Purchase purchase) throws ServiceUnavailableException {
		purchase.setStatus(Status.DELETED);
		savePurchase(purchase);
	}

	@Override
	public Purchase findByBoId(String boId) throws ServiceUnavailableException {
		return findByBoId(boId, this::hibernateInitializePurchase);
	}

	public Purchase findByBoId(String boId, Consumer<Purchase> initializer) {
		String queryStr = "from Purchase purchase where purchase.boId=:dataInput and purchase.status != :status";
		List<Purchase> purchaseList = purchaseDao.findByString(queryStr, boId);
		Purchase purchase = purchaseList.get(0);
		initializer.accept(purchase);
		return purchase;
	}

	@Override
	public List<Purchase> findByName(String name) throws ServiceUnavailableException {
		String queryStr = "select purchase from Purchase purchase where purchase.name=:dataInput";
		List<Purchase> purchaseList = purchaseDao.findByString(queryStr, name);
		hibernateInitializePurchaseList(purchaseList);
		return purchaseList;
	}

	@Override
	public Map<String, Integer> monthlyPurchaseReport(Date startDate, Date endDate) throws ServiceUnavailableException {
		List<Purchase> purchaseList = findByPeriod(startDate, endDate);
		List<PurchaseOrder> purchaseOrderList = mergePurchaseOrderOfPurchaseList(purchaseList);
		Map<String, List<PurchaseOrder>> purchaseOrderMap = groupPurchaseOrderByProduct(purchaseOrderList);
		return countQuantityByProduct(purchaseOrderMap);
	}

	private List<PurchaseOrder> mergePurchaseOrderOfPurchaseList(List<Purchase> purchaseList) {
		List<PurchaseOrder> purchaseOrderList = new ArrayList<>();
		for (Purchase purchase : purchaseList) {
			purchaseOrderList.addAll(purchase.getPurchaseOrderList());
		}
		return purchaseOrderList;
	}

	private Map<String, Integer> countQuantityByProduct(Map<String, List<PurchaseOrder>> purchaseOrderMap) {

		Map<String, Integer> resultMap = new HashMap<>();
		for (List<PurchaseOrder> list : purchaseOrderMap.values()) {
			int count = 0;
			for (PurchaseOrder po : list) {
				count += po.getQuantity();
			}
			resultMap.put(list.get(0).getProduct().getProductName(), count);
		}
		return resultMap;
	}

	private Map<String, List<PurchaseOrder>> groupPurchaseOrderByProduct(List<PurchaseOrder> purchaseOrderList) {
		Map<String, List<PurchaseOrder>> purchaseOrderMap = new HashMap<>();
		for (PurchaseOrder purchaseOrder : purchaseOrderList) {
			List<PurchaseOrder> list = purchaseOrderMap.get(purchaseOrder.getProduct().getBoId());
			if (list == null) {
				list = new ArrayList<>();
				purchaseOrderMap.put(purchaseOrder.getProduct().getBoId(), list);
			}
			list.add(purchaseOrder);
		}
		return purchaseOrderMap;
	}

}
