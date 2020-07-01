package com.khayayphyu.resource.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.khayayphyu.domain.SaleOrder;
import com.khayayphyu.domain.SalePrice;
import com.khayayphyu.domain.constant.Status;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.domain.jsonviews.SummaryView;
import com.khayayphyu.domain.jsonviews.Views;
import com.khayayphyu.resource.SaleServiceResource;
import com.khayayphyu.service.SaleOrderService;
import com.khayayphyu.service.SalePriceService;
import com.khayayphyu.service.SaleService;

@RestController
@RequestMapping(value = "/sale")
public class SaleServiceResourceImpl extends AbstractServiceResourceImpl implements SaleServiceResource {
	Logger logger = Logger.getLogger(SaleServiceResourceImpl.class);

	@Autowired
	private SaleService saleService;
	
	@Autowired
	private SaleOrderService saleOrderService;
	
	@Autowired
	private SalePriceService salePriceService;

	@RequestMapping(method = RequestMethod.POST, value = "")
	@Override
	public boolean create(@RequestBody Sale sale) throws ServiceUnavailableException {
		sale.setSaleOrderList(removeDupliateSaleOrder(sale.getSaleOrderList()));
		updatePriceToSaleOrder(sale.getSaleOrderList().get(0));
		return sale.isNew()? save(sale):updateSale(sale);
	}
	
	private boolean updateSale(Sale sale)throws ServiceUnavailableException {
		saleOrderService.removeSaleOrderListOf(sale);
		return save(sale);
	}

	private boolean save(Sale sale) {
		sale.getSaleOrderList().forEach(saleOrder -> resetSaleOrder(saleOrder, sale));
		try {
			sale.getSaleOrderList().forEach(so -> so.setSale(sale));
			if (!saleService.syncWithDb(sale)) {
				return false;
			}
			saleService.saveSale(sale);
		} catch (ServiceUnavailableException e) {
			logger.error("Can't save sale", e);
			return false;
		}
		return true;
	}

	private void resetSaleOrder(SaleOrder itsaleOrder, Sale parent) {
		itsaleOrder.setSale(parent);
		itsaleOrder.setStatus(Status.OPEN);
		itsaleOrder.setId(0);
	}


	private void updatePriceToSaleOrder(SaleOrder saleOrder){
		SalePrice salePrice;
		
		try {
			salePrice = salePriceService.findByProduct(saleOrder.getProduct());
			saleOrder.setPrice(salePrice.getAmount());
			saleOrder.setAmount(saleOrder.getPrice() * saleOrder.getQuantity());
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
		
	}
	
	private List<SaleOrder> removeDupliateSaleOrder(List<SaleOrder> saleOrderList) {
		Map<String, SaleOrder> map = new HashMap<>();
		saleOrderList.forEach(saleOrder -> {
			String productBoId = saleOrder.getProduct().getBoId();
			SaleOrder temp = map.get(productBoId);
			if(temp == null) { //if duplicate product is null 
				map.put(productBoId, saleOrder);
			}else {
				temp.setQuantity(temp.getQuantity() + saleOrder.getQuantity());
			}
		});
		saleOrderList = new ArrayList<>();
		saleOrderList.addAll(map.values());
		return saleOrderList;
	}
	
	

//	@RequestMapping(method = RequestMethod.POST)
//	@Override
//	public boolean createSale(@RequestBody Sale sale) throws ServiceUnavailableException {
//		sale.setSaleOrderList(removeDupliateSaleOrder(sale.getSaleOrderList()));
//		sale.getSaleOrderList().forEach(this::updatePriceToSaleOrder);
//		sale.getSaleOrderList().forEach(so -> so.setSale(sale));
//		if (!saleService.syncWithDb(sale)) {
//			return false;
//		}
//		
//		saleService.saveSale(sale);
//		return true;
//	}

	@Override
	@JsonView(Views.InnerSummary.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{boId}")
	public Sale findBySaleBoId(HttpServletRequest request, @PathVariable String boId)
			throws ServiceUnavailableException {
		return saleService.findByBoId(boId, saleService::detailInitializer);
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
	public boolean deleteSale(@PathVariable String boId) throws ServiceUnavailableException {
		saleService.deleteSale(saleService.findByBoId(boId));
		return true;
	}

}
