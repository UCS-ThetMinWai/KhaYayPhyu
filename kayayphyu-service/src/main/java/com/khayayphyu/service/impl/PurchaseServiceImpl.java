package com.khayayphyu.service.impl;

import java.util.Date;
import java.util.List;

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
	
	public void ensurePurchaseBoId(Purchase purchase) {
		if(CollectionUtils.isEmpty(purchase.getPurchaseOrderList())) 
			return;
		for (PurchaseOrder purchaseOrder : purchase.getPurchaseOrderList()) {
			if (purchaseOrder.isBoIdRequired()) {
				purchaseOrder.setBoId(purchaseOrderService.getNextBoId(EntityType.PURCHASEORDER));
			}
		}
		if(purchase.getCustomer() == null || purchase.getUser() == null) {
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
		return purchaseList;
	}

	@Override
	@Transactional(readOnly = false)
	public void savePurchase(Purchase purchase) throws ServiceUnavailableException {
		if(purchase.isBoIdRequired()) {
			purchase.setBoId(getNextBoId(EntityType.PURCHASE));
			ensurePurchaseBoId(purchase);
		}
		purchaseDao.save(purchase);
	}
	
	@Override
	public long getCount() {
		return purchaseDao.getCount("select count(purchase) from Purchase purchase");
	}

	@Override
	public List<Purchase> findByPeriod(Date startDate, Date endDate)throws ServiceUnavailableException {
		String queryString = "from Purchase purchase where purchase.purchaseDate between :dataInput and :dataInput1";
		List<Purchase> purchaseList = purchaseDao.findByDate(queryString, startDate, endDate);
		return purchaseList;
	}

	@Override
	public void hibernateInitializePurchaseList(List<Purchase> purchaseList) {
		Hibernate.initialize(purchaseList);
		if(CollectionUtils.isEmpty(purchaseList))
			return;
		for(Purchase purchase : purchaseList) {
			hibernateInitializePurchase(purchase);
		}
	}
	
	@Override
	public void hibernateInitializePurchase(Purchase purchase) {
		Hibernate.initialize(purchase);
		if(purchase == null)
			return;
		customerService.hibernateInitializeCustomer(purchase.getCustomer());
		Hibernate.initialize(purchase.getUser());
		
		for(PurchaseOrder purchaseOrder : purchase.getPurchaseOrderList()) {
			purchaseOrderService.hibernateInitializePurchaseOrder(purchaseOrder);
		}
	}



	@Override
	public List<Purchase> findByBoId(String boId) throws ServiceUnavailableException {
		String queryStr = "select purchase from Purchase purchase where purchase.boId=:dataInput";
		List<Purchase> purchaseList = purchaseDao.findByString(queryStr, boId);
		if (CollectionUtils.isEmpty(purchaseList))
			return null;
		hibernateInitializePurchaseList(purchaseList);
		return purchaseList;
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
