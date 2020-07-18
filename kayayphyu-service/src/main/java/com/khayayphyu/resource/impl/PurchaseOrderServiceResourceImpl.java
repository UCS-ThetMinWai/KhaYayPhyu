package com.khayayphyu.resource.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.khayayphyu.domain.PurchaseOrder;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.resource.PurchaseOrderServiceResource;
import com.khayayphyu.service.PurchaseOrderService;

@RestController
@RequestMapping(value = { "/purchaseOrder" })
public class PurchaseOrderServiceResourceImpl extends AbstractServiceResourceImpl
		implements PurchaseOrderServiceResource {

	@Autowired
	private PurchaseOrderService purchaseOrderService;

	@RequestMapping(method = RequestMethod.POST, value = "")
	@Override
	public Boolean createPurchaseOrder(HttpServletRequest request,@RequestBody PurchaseOrder purchaseOrder) {
		try {
			purchaseOrderService.savePurchaseOrder(purchaseOrder);
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "boId/{boId}")
	@Override
	public PurchaseOrder findByBoId(HttpServletRequest request,@PathVariable String boId) throws ServiceUnavailableException {
		return purchaseOrderService.findByBoId(boId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/period/{startDate}/{endDate}")
	@Override
	public List<PurchaseOrder> findByPeriod(HttpServletRequest request,@RequestParam Date startDate,@RequestParam Date endDate)
			throws ServiceUnavailableException {
		return purchaseOrderService.findByPeriod(startDate, endDate);
	}

	@RequestMapping(method = RequestMethod.GET, value = "")
	@Override
	public List<PurchaseOrder> getAllPurchaseOrder(HttpServletRequest request) throws ServiceUnavailableException {
		return purchaseOrderService.getAllPurchaseOrder();
	}

}
