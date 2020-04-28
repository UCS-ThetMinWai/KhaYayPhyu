package com.khayayphyu.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.PurchaseOrderDao;
import com.khayayphyu.domain.Customer;
import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.Purchase;
import com.khayayphyu.domain.PurchaseOrder;
import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.CustomerService;
import com.khayayphyu.service.ProductService;
import com.khayayphyu.service.PurchaseOrderService;
import com.khayayphyu.service.PurchaseService;
@Service("purchaseOrderService")
@Transactional(readOnly = true)
public class PurchaseOrderServiceImpl extends AbstractServiceImpl<PurchaseOrder> implements PurchaseOrderService {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PurchaseOrderDao purchaseOrderDao;
	
	public void ensurePurchaseOrderBoId(PurchaseOrder purchaseOrder) {
		if(purchaseOrder.getProduct() == null || purchaseOrder.getCustomer() == null || purchaseOrder.getPurchase() == null) {
			return;
		}
		Product product = purchaseOrder.getProduct();
		product.setBoId(productService.getNextBoId(EntityType.PRODUCT));
		
		Customer customer = purchaseOrder.getCustomer();
		customer.setBoId(customerService.getNextBoId(EntityType.CUSTOMER));
		
		Purchase purchase = purchaseOrder.getPurchase();
		purchase.setBoId(purchaseService.getNextBoId(EntityType.PURCHASE));
	}

	@Transactional(readOnly = false)
	@Override
	public void savePurchaseOrder(PurchaseOrder purchaseOrder) throws ServiceUnavailableException {
		if(purchaseOrder.isBoIdRequired()) {
			purchaseOrder.setBoId(getNextBoId(EntityType.PURCHASEORDER));
			ensurePurchaseOrderBoId(purchaseOrder);
		}
		purchaseOrderDao.save(purchaseOrder);
	}
	
	@Override
	public List<PurchaseOrder> findByBoId(String boId) throws ServiceUnavailableException {
		String queryStr = "from PurchaseOrder purchaseOrder where purchaseOrder.boId=:dataInput";
		List<PurchaseOrder> purchaseOrderList = purchaseOrderDao.findByString(queryStr, boId);
		if (CollectionUtils.isEmpty(purchaseOrderList))
			return null;
		hibernateInitializePurchaseOrderList(purchaseOrderList);
		return purchaseOrderList;
	}
	
	@Override
	public List<PurchaseOrder> findByPeriod(Date startDate, Date endDate)throws ServiceUnavailableException {
		String queryString = "from PurchaseOrder purchaseOrder where purchaseOrder.date between :dataInput and :dataInput1";
		List<PurchaseOrder> purchaseOrderList = purchaseOrderDao.findByDate(queryString, startDate, endDate);
		return purchaseOrderList;
	}

	@Override
	public long getCount() {
		return purchaseOrderDao.getCount("select count(purchaseOrder) from PurchaseOrder purchaseOrder");
	}

	@Override
	public void hibernateInitializePurchaseOrderList(List<PurchaseOrder> purchaseOrderList) {
		Hibernate.initialize(purchaseOrderList);
		if (CollectionUtils.isEmpty(purchaseOrderList))
			return;

		for (PurchaseOrder purchaseOrder : purchaseOrderList) {
			hibernateInitializePurchaseOrder(purchaseOrder);
		}
	}

	@Override
	public void hibernateInitializePurchaseOrder(PurchaseOrder purchaseOrder) {
		Hibernate.initialize(purchaseOrder);
		if(purchaseOrder == null) {
			return;
		}
		
		customerService.hibernateInitializeCustomer(purchaseOrder.getCustomer());
		productService.hibernateInitializeProduct(purchaseOrder.getProduct());
	}

}
