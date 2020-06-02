package com.khayayphyu.resource;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.khayayphyu.domain.PurchaseOrder;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface PurchaseOrderServiceResource {
	Boolean createPurchaseOrder(HttpServletRequest request, PurchaseOrder purchaseOrder);
	PurchaseOrder findByBoId(HttpServletRequest request, String boId)throws ServiceUnavailableException;
	List<PurchaseOrder> findByPeriod(HttpServletRequest request, Date startDate, Date endDate)throws ServiceUnavailableException;
	List<PurchaseOrder> getAllPurchaseOrder(HttpServletRequest request)throws ServiceUnavailableException;
}
