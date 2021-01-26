package com.khayayphyu.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.PurchaseOrderDao;
import com.khayayphyu.domain.Purchase;
import com.khayayphyu.domain.PurchaseOrder;
import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.ProductService;
import com.khayayphyu.service.PurchaseOrderService;
@Service("purchaseOrderService")
@Transactional(readOnly = true)
public class PurchaseOrderServiceImpl extends AbstractServiceImpl<PurchaseOrder> implements PurchaseOrderService {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private PurchaseOrderDao purchaseOrderDao;

	@Transactional(readOnly = false)
	@Override
	public void savePurchaseOrder(PurchaseOrder purchaseOrder) throws ServiceUnavailableException {
		if(purchaseOrder.isNew()) {
			purchaseOrder.setBoId(getNextBoId(EntityType.PURCHASEORDER));
		}
		purchaseOrderDao.save(purchaseOrder);
	}
	
	@Override
	public PurchaseOrder findByBoId(String boId) throws ServiceUnavailableException {
		String queryStr = "from PurchaseOrder purchaseOrder where purchaseOrder.boId=:dataInput";
		List<PurchaseOrder> purchaseOrderList = purchaseOrderDao.findByString(queryStr, boId);
		if (CollectionUtils.isEmpty(purchaseOrderList))
			return null;
		hibernateInitializePurchaseOrderList(purchaseOrderList);
		return purchaseOrderList.get(0);
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
	public List<PurchaseOrder> getAllPurchaseOrder() throws ServiceUnavailableException {
		List<PurchaseOrder> purchaseOrderList = purchaseOrderDao.getAll("From PurchaseOrder purchaseOrder");
		hibernateInitializePurchaseOrderList(purchaseOrderList);
		return purchaseOrderList;
	}

	@Override
	public void hibernateInitializePurchaseOrderList(List<PurchaseOrder> purchaseOrderList) {
		purchaseOrderList.forEach(this::hibernateInitializePurchaseOrder);
	}

	@Override
	public void hibernateInitializePurchaseOrder(PurchaseOrder purchaseOrder) {
		if(purchaseOrder == null) {
			return;
		}	
		productService.hibernateInitializeProduct(purchaseOrder.getProduct());
	}
	
	@Transactional(readOnly = false)
	@Override
	public void removePurchaseOrderListOf(Purchase purchase)throws ServiceUnavailableException {
		List<PurchaseOrder> purchaseOrderList = purchaseOrderDao.findByStringWithoutStatus("from PurchaseOrder purchaseOrder where purchaseOrder.purchase = :dataInput", purchase);
		for(PurchaseOrder purchaseOrder : purchaseOrderList) {
			purchaseOrderDao.delete(purchaseOrder);
		}
	}

}
