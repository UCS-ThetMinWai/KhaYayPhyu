package com.khayayphyu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.SaleDao;
import com.khayayphyu.domain.Sale;
import com.khayayphyu.domain.SaleOrder;
import com.khayayphyu.domain.constant.Status;
import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.SaleOrderService;
import com.khayayphyu.service.SaleService;

@Service("saleService")
@Transactional(readOnly = true)
public class SaleServiceImpl extends AbstractServiceImpl<Sale> implements SaleService {

	@Autowired
	private SaleDao saleDao;

	@Autowired
	private SaleOrderService saleOrderService;

	public void ensuredSaleBoId(Sale sale) {

		if (CollectionUtils.isEmpty(sale.getSaleOrderList()))
			return;
		for (SaleOrder saleOrder : sale.getSaleOrderList()) {
			if (saleOrder.isBoIdRequired()) {
				saleOrder.setBoId(saleOrderService.getNextBoId(EntityType.SALEORDER));
			}
		}
	}

	@Transactional(readOnly = false)
	@Override
	public void SaveSale(Sale sale) throws ServiceUnavailableException {
		if (sale.isBoIdRequired()) {
			sale.setBoId(getNextBoId(EntityType.SALE));
			sale.setStatus(Status.OPEN);
			ensuredSaleBoId(sale);
		}
		if (sale.getBalance() == 0) {
			sale.setStatus(Status.CLOSE);
		} else {
			sale.setStatus(Status.OPEN);
		}

		saleDao.save(sale);
	}

	@Override
	public List<Sale> findByName(String name) throws ServiceUnavailableException {
		String queryStr = "from Sale sale where sale.name=:dataInput";
		List<Sale> saleList = saleDao.findByString(queryStr, name);
		if (CollectionUtils.isEmpty(saleList))
			return null;
		hibernateInitializeSaleList(saleList);
		return saleList;
	}

	@Override
	public Sale findByBoId(String boId) throws ServiceUnavailableException {
		String queryStr = "select sale from Sale sale where sale.boId=:dataInput and sale.status != :status";
		List<Sale> saleList = saleDao.findByString(queryStr, boId);
		if (CollectionUtils.isEmpty(saleList))
			return null;
		hibernateInitializeSaleList(saleList);
		return saleList.get(0);
	}

	@Override
	public List<Sale> findByPeriod(Date startDate, Date endDate) throws ServiceUnavailableException {
		String queryString = "from Sale sale where sale.saleDate between :dataInput and :dataInput1";
		List<Sale> saleList = saleDao.findByDate(queryString, startDate, endDate);
		if (CollectionUtils.isEmpty(saleList))
			return null;
		hibernateInitializeSaleList(saleList);
		return saleList;
	}

	@Override
	public List<Sale> getAllSale() throws ServiceUnavailableException {
		List<Sale> saleList = saleDao.getAll("From Sale sale");
		hibernateInitializeSaleList(saleList);
		return saleList;
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteSale(Sale sale) throws ServiceUnavailableException {
		sale.setStatus(Status.DELETED);
		SaveSale(sale);
	}

	@Override
	public void hibernateInitializeSaleList(List<Sale> saleList) {
		Hibernate.initialize(saleList);
		if (CollectionUtils.isEmpty(saleList))
			return;
		for (Sale sale : saleList) {
			hibernateInitializeSale(sale);
		}
	}

	@Override
	public void hibernateInitializeSale(Sale sale) {
		Hibernate.initialize(sale);
		if (sale == null)
			return;
		for (SaleOrder saleOrder : sale.getSaleOrderList()) {
			saleOrderService.hibernateInitializeSaleOrder(saleOrder);
		}
	}

	@Override
	public long getCount() {
		return saleDao.getCount("select count(sale) from Sale sale");
	}

	@Override
	public Map<String, Integer> monthlySaleReport(Date startDate, Date endDate) throws ServiceUnavailableException {
		List<Sale> saleList = findByPeriod(startDate, endDate);
		List<SaleOrder> saleOrderList = mergeSaleOrderOfSaleList(saleList);
		Map<String, List<SaleOrder>> saleOrderMap = groupSaleOrderByProduct(saleOrderList);
		return countQuantityByProduct(saleOrderMap);
	}
	
	private List<SaleOrder> mergeSaleOrderOfSaleList(List<Sale> saleList) {
		List<SaleOrder> saleOrderList = new ArrayList<>();
		for(Sale sale: saleList) {
			saleOrderList.addAll(sale.getSaleOrderList());
		}
		return saleOrderList;
	}
	
	private Map<String, Integer> countQuantityByProduct(Map<String, List<SaleOrder>> saleOrderMap) {
		
		Map<String, Integer> resultMap = new HashMap<>();
		for(List<SaleOrder> list : saleOrderMap.values()) {
			int count = 0;
			for(SaleOrder so : list) {
				count += so.getQuantity();
			}
			resultMap.put(list.get(0).getProduct().getProductName(), count);
		}
		return resultMap;
	}
	
	private Map<String, List<SaleOrder>> groupSaleOrderByProduct(List<SaleOrder> saleOrderList) {
		Map<String, List<SaleOrder>> saleOrderMap = new HashMap<>();
		for(SaleOrder saleOrder : saleOrderList) {
			List<SaleOrder> list = saleOrderMap.get(saleOrder.getProduct().getBoId());
			if(list == null) {
				list =new ArrayList<>();
				saleOrderMap.put(saleOrder.getProduct().getBoId(), list);
			}
			list.add(saleOrder);
		}
		return saleOrderMap;
	}
}