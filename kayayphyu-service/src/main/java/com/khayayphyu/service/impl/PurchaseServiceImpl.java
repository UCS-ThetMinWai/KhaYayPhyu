package com.khayayphyu.service.impl;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.PurchaseDao;
import com.khayayphyu.domain.Customer;
import com.khayayphyu.domain.Purchase;
import com.khayayphyu.domain.PurchaseOrder;
import com.khayayphyu.domain.User;
import com.khayayphyu.domain.constant.Status;
import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.CustomerService;
import com.khayayphyu.service.PurchaseOrderService;
import com.khayayphyu.service.PurchaseService;
import com.khayayphyu.service.UserService;

@Transactional(readOnly = true)
@Service("purchaseService")
public class PurchaseServiceImpl extends AbstractServiceImpl<Purchase> implements PurchaseService {
	@Autowired
	private CustomerService customerService;

	@Autowired
	private PurchaseOrderService purchaseOrderService;

	@Autowired
	private UserService userService;

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

	public void ensurePurchaseBoId(Purchase purchase) {
		if (CollectionUtils.isEmpty(purchase.getPurchaseOrderList()))
			return;
		for (PurchaseOrder purchaseOrder : purchase.getPurchaseOrderList()) {
			if (purchaseOrder.isBoIdRequired()) {
				purchaseOrder.setBoId(purchaseOrderService.getNextBoId(EntityType.PURCHASEORDER));
			}
		}
		if (purchase.getCustomer() == null || purchase.getUser() == null) {
			return;
		}

		Customer customer = purchase.getCustomer();
		customer.setBoId(customerService.getNextBoId(EntityType.CUSTOMER));

		User user = purchase.getUser();
		user.setBoId(userService.getNextBoId(EntityType.USER));
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
		if (purchase.isBoIdRequired()) {
			purchase.setBoId(getNextBoId(EntityType.PURCHASE));
			purchase.setStatus(Status.OPEN);

			ensurePurchaseBoId(purchase);
		}
		if (purchase.getBalance() == 0) {
			purchase.setStatus(Status.CLOSE);
		} else {
			purchase.setStatus(Status.OPEN);
		}
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
		if (purchaseList.isEmpty())
			return null;
		hibernateInitializePurchaseList(purchaseList);
		return purchaseList;
	}

	@Override
	public void hibernateInitializePurchaseList(List<Purchase> purchaseList) {
		Hibernate.initialize(purchaseList);
		if (CollectionUtils.isEmpty(purchaseList))
			return;
		for (Purchase purchase : purchaseList) {
			hibernateInitializePurchase(purchase);
		}
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
		if (CollectionUtils.isEmpty(purchaseList))
			return null;
		Purchase purchase = purchaseList.get(0);
		initializer.accept(purchase);
		return purchase;
	}

	@Override
	public List<Purchase> findByName(String name) throws ServiceUnavailableException {
		String queryStr = "select purchase from Purchase purchase where purchase.name=:dataInput";
		List<Purchase> purchaseList = purchaseDao.findByString(queryStr, name);
		if (CollectionUtils.isEmpty(purchaseList))
			return null;
		hibernateInitializePurchaseList(purchaseList);
		return purchaseList;
	}

}
