package com.khayayphyu.resource.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.khayayphyu.domain.SaleOrder;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.resource.SaleOrderServiceResource;
import com.khayayphyu.service.SaleOrderService;

@RestController
@RequestMapping(value = "/saleOrder")
public class SaleOrderServiceResourceImpl extends AbstractServiceResourceImpl implements SaleOrderServiceResource {

	@Autowired
	private SaleOrderService saleOrderService;
	
	@RequestMapping(method = RequestMethod.POST)
	@Override
	public Boolean createSaleOrder(HttpServletRequest request,@RequestBody SaleOrder saleOrder) {
		try {
			saleOrderService.saveSaleOrder(saleOrder);
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "boId/{boId}")
	@Override
	public SaleOrder findBySaleOrderBoId(HttpServletRequest request,@PathVariable String boId)
			throws ServiceUnavailableException {
		return saleOrderService.findByBoId(boId);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/list")
	@Override
	public List<SaleOrder> getAllSaleOrder(HttpServletRequest request) throws ServiceUnavailableException {
		
		return saleOrderService.getAllSaleOrder();
	}

}
