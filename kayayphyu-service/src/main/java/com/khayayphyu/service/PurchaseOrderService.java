package com.khayayphyu.service;

import java.util.Date;
import java.util.List;

import com.khayayphyu.domain.PurchaseOrder;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface PurchaseOrderService extends AbstractService<PurchaseOrder> {
	public List<PurchaseOrder> findByPeriod(Date startDate, Date endDate)throws ServiceUnavailableException;
	public void savePurchaseOrder(PurchaseOrder purchaseOrder)throws ServiceUnavailableException;
	public List<PurchaseOrder> getAllPurchaseOrder()throws ServiceUnavailableException;
	public void hibernateInitializePurchaseOrderList(List<PurchaseOrder> purchaseOrderList);
	public void hibernateInitializePurchaseOrder(PurchaseOrder purchaseOrder);
}
