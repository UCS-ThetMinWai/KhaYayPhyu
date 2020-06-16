package com.khayayphyu.resource.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
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
import com.khayayphyu.domain.Sale;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.domain.jsonviews.DetailView;
import com.khayayphyu.domain.jsonviews.SummaryView;
import com.khayayphyu.resource.SaleServiceResource;
import com.khayayphyu.service.SaleService;

@RestController
@RequestMapping(value = "/sale")
public class SaleServiceResourceImpl extends AbstractServiceResourceImpl implements SaleServiceResource {

	@Autowired
	private SaleService saleService;

	private static Logger logger = Logger.getLogger(SaleServiceResourceImpl.class);

	@RequestMapping(method = RequestMethod.POST)
	@Override
	public boolean createSale(@RequestBody Sale sale) throws ServiceUnavailableException {
		sale.getSaleOrderList().forEach(so -> {
			so.setSale(sale);
			so.setPrice(so.getProduct().getSalePrice().getAmount());
		});
		saleService.saveSale(sale);
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{boId}")
	@Override
	public Sale findBySaleBoId(HttpServletRequest request, @PathVariable String boId)
			throws ServiceUnavailableException {
		return saleService.findByBoId(boId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/search/{name}")
	@Override
	public List<Sale> findByName(HttpServletRequest request, @PathVariable String name)
			throws ServiceUnavailableException {
		return saleService.findByName(name);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/period/")
	@Override
	public List<Sale> findByPeriod(HttpServletRequest request,
			@RequestParam("startDate") @DateTimeFormat(iso = ISO.DATE) Date startDate,
			@RequestParam("endDate") @DateTimeFormat(iso = ISO.DATE) Date endDate) throws ServiceUnavailableException {
		return saleService.findByPeriod(startDate, endDate);
	}

	@Override
	@JsonView(SummaryView.class)
	@RequestMapping(method = RequestMethod.GET, value = "")
	public List<Sale> getAllSale(HttpServletRequest request) throws ServiceUnavailableException {
		return saleService.getAllSale();
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{boId}")
	@Override
	@JsonView(DetailView.class)
	public boolean deleteSale(@PathVariable String boId) throws ServiceUnavailableException {
		saleService.deleteSale(saleService.findByBoId(boId));
		return true;
	}

}
