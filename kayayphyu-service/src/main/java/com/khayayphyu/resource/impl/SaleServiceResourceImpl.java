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

import com.khayayphyu.domain.Sale;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.resource.SaleServiceResource;
import com.khayayphyu.service.SaleService;

@RestController
@RequestMapping(value = "/sale")
public class SaleServiceResourceImpl extends AbstractServiceResourceImpl implements SaleServiceResource {

	@Autowired
	private SaleService saleService;
	
	@RequestMapping(method = RequestMethod.POST)
	@Override
	public Boolean createSale(HttpServletRequest request,@RequestBody Sale sale) {
		try {
			saleService.SaveSale(sale);
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/boId/{boId}")
	@Override
	public List<Sale> findBySaleBoId(HttpServletRequest request,@PathVariable String boId) throws ServiceUnavailableException {
		return saleService.findByBoId(boId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/name/{name}")
	@Override
	public List<Sale> findByName(HttpServletRequest request,@PathVariable String name) throws ServiceUnavailableException {
		return saleService.findByName(name);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/period/{startDate}/{endDate}")
	@Override
	public List<Sale> findByPeriod(HttpServletRequest request,@RequestParam Date startDate,@RequestParam Date endDate)
			throws ServiceUnavailableException {
		return saleService.findByPeriod(startDate, endDate);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/list")
	@Override
	public List<Sale> getAllSale(HttpServletRequest request) throws ServiceUnavailableException {
		return saleService.getAllSale();
	}

}
