package com.khayayphyu.resource.impl;

import java.util.Date;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.khayayphyu.domain.Price;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.resource.PriceServiceResource;
import com.khayayphyu.service.PriceService;

@RestController
@RequestMapping(value= {"/price"})
public class PriceServiceResourceImpl extends AbstractServiceResourceImpl implements PriceServiceResource {
	
	private Logger logger = Logger.getLogger(PriceServiceResourceImpl.class);
	
	@Autowired
	private PriceService priceService;

	@RequestMapping(method = RequestMethod.POST, value = "")
	@Override
	public Boolean createPrice(HttpServletRequest request,@RequestBody Price price) {
		try {
			priceService.savePrice(price);
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "boId/{boId}")
	@Override
	public List<Price> findByPriceBoId(HttpServletRequest request,@PathVariable String boId) throws ServiceUnavailableException {
		logger.info("boId " + boId);
		return priceService.findByBoId(boId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "period/{startDate}/{endDate}")
	@Override
	public List<Price> findByPeriod(HttpServletRequest request,@RequestParam Date startDate,@RequestParam Date endDate)
			throws ServiceUnavailableException {
		return priceService.findByPeriod(startDate, endDate);
	}

}
