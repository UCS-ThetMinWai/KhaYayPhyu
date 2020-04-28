package com.khayayphyu.resource.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.khayayphyu.domain.Purchase;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.resource.PurchaseServiceResource;
import com.khayayphyu.service.PurchaseService;

@RestController
@RequestMapping(value = {"/purchase"})
public class PurchaseServiceResourceImpl extends AbstractServiceResourceImpl implements PurchaseServiceResource {
	
	@Autowired
	private PurchaseService purchaseService;
	
	@RequestMapping(method = RequestMethod.POST, value = "")
	@Override
	public Boolean createPurchase(HttpServletRequest request,@RequestBody Purchase purchase) {
		try {
			purchaseService.savePurchase(purchase);
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/boId/{boId}")
	@Override
	public List<Purchase> findByPurchaseBoId(HttpServletRequest request,@PathVariable String boId)
			throws ServiceUnavailableException {
		return purchaseService.findByBoId(boId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/name/{name}")
	@Override
	public List<Purchase> findByName(HttpServletRequest request,@PathVariable String name) throws ServiceUnavailableException {
		return purchaseService.findByName(name);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	@Override
	public List<Purchase> getAllPurchase(HttpServletRequest request)throws ServiceUnavailableException {
		return purchaseService.getAllPurchase();
	}

}
