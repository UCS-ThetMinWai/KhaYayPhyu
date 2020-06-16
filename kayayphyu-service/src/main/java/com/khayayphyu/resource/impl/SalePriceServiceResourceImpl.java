package com.khayayphyu.resource.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.khayayphyu.domain.SalePrice;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.resource.SalePriceServiceResource;
import com.khayayphyu.service.SalePriceService;

@RestController
@RequestMapping(value= {"/salePrice"})
public class SalePriceServiceResourceImpl extends AbstractServiceResourceImpl implements SalePriceServiceResource {
	
	private Logger logger = Logger.getLogger(SalePriceServiceResourceImpl.class);
	
	@Autowired
	private SalePriceService priceService;

	@RequestMapping(method = RequestMethod.POST, value = "")
	@Override
	public Boolean createPrice(HttpServletRequest request,@RequestBody SalePrice price) {
		try {
			priceService.savePrice(price);
			logger.info(price);
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
		return true;
	}

	
	@RequestMapping(method = RequestMethod.POST, value = "period/{startDate}/{endDate}")
	@Override
	public List<SalePrice> findByPeriod(HttpServletRequest request,@RequestParam Date startDate,@RequestParam Date endDate)
			throws ServiceUnavailableException {
		return priceService.findByPeriod(startDate, endDate);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	@Override
	public List<SalePrice> getAllPrice(HttpServletRequest request) throws ServiceUnavailableException {
		return priceService.getAllPrice();
	}

}
