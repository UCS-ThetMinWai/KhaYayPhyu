package com.khayayphyu.resource.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.khayayphyu.domain.Purchase;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.domain.jsonviews.SummaryView;
import com.khayayphyu.domain.jsonviews.Views;
import com.khayayphyu.resource.PurchaseServiceResource;
import com.khayayphyu.service.PurchaseService;
import com.khayayphyu.service.impl.PurchaseServiceImpl;

@RestController
@RequestMapping(value = { "/purchase" })
public class PurchaseServiceResourceImpl extends AbstractServiceResourceImpl implements PurchaseServiceResource {

	@Autowired
	private PurchaseService purchaseService;

	@RequestMapping(method = RequestMethod.POST, value = "")
	@Override
	public Boolean createPurchase(HttpServletRequest request, @RequestBody Purchase purchase) {
		purchase.getPurchaseOrderList().forEach(po -> {
			po.setPurchase(purchase);
			po.setPrice(po.getProduct().getPurchasePrice().getAmount());
		});
		try {
			purchaseService.savePurchase(purchase);
		} catch (ServiceUnavailableException e) {
			return false;
		}
		return true;
	}

	@Override
	@JsonView(Views.Comprehensive.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{boId}")
	public Purchase findByPurchaseBoId(HttpServletRequest request, @PathVariable String boId) throws ServiceUnavailableException {
		return purchaseService.findByBoId(boId, PurchaseServiceImpl::detailInitializer);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search/{name}")
	@Override
	public List<Purchase> findByName(HttpServletRequest request, @PathVariable String name) throws ServiceUnavailableException {
		return purchaseService.findByName(name);
	}

	@Override
	@JsonView(SummaryView.class)
	@RequestMapping(method = RequestMethod.GET, value = "")
	public List<Purchase> getAllPurchase(HttpServletRequest request) throws ServiceUnavailableException {
		return purchaseService.getAllPurchase();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{boId}")
	@Override
	public boolean deletePurchase(@PathVariable("boId") String boId) throws ServiceUnavailableException {
		purchaseService.deletePurchase(purchaseService.findByBoId(boId));
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/period/")
	@Override
	public List<Purchase> findByPeriod(HttpServletRequest request, @RequestParam("startDate") @DateTimeFormat(iso = ISO.DATE) Date startDate, @RequestParam("endDate") @DateTimeFormat(iso = ISO.DATE) Date endDate) throws ServiceUnavailableException {
		return purchaseService.findByPeriod(startDate, endDate);
	}

}
