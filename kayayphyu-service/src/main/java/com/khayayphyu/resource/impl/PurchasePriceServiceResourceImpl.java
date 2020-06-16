package com.khayayphyu.resource.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.khayayphyu.domain.PurchasePrice;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.resource.PurchasePriceServiceResource;
import com.khayayphyu.service.PurchasePriceService;

@RestController
@RequestMapping(value= {"/purchasePrice"})
public class PurchasePriceServiceResourceImpl extends AbstractServiceResourceImpl implements PurchasePriceServiceResource {
	
	@Autowired
	private PurchasePriceService purchasePriceService;

	@RequestMapping(method = RequestMethod.POST, value = "")
	@Override
	public boolean createPrice(HttpServletRequest request, PurchasePrice price) {
		try {
			purchasePriceService.savePrice(price);
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
		return true;
	}

	@RequestMapping(method = RequestMethod.PUT, value = "period/{startDate}/{endDate}")
	@Override
	public List<PurchasePrice> findByPeriod(HttpServletRequest request,@RequestParam Date startDate,@RequestParam Date endDate)
			throws ServiceUnavailableException {
			return purchasePriceService.findByPeriod(startDate, endDate);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	@Override
	public List<PurchasePrice> getAllPrice(HttpServletRequest request) throws ServiceUnavailableException {
		return purchasePriceService.getAllPrice();
	}

}
