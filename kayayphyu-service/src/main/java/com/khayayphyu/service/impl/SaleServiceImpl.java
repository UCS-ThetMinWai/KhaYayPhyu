package com.khayayphyu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.SaleDao;
import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.Sale;
import com.khayayphyu.domain.SaleOrder;
import com.khayayphyu.domain.constant.Status;
import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.ProductService;
import com.khayayphyu.service.SaleOrderService;
import com.khayayphyu.service.SaleService;

@Service("saleService")
@Transactional(readOnly = true)
public class SaleServiceImpl extends AbstractServiceImpl<Sale> implements SaleService {

	@Autowired
	private SaleDao saleDao;

	@Autowired
	private ProductService productService;

	@Autowired
	private SaleOrderService saleOrderService;

	public void ensuredSaleBoId(Sale sale) {

		if (CollectionUtils.isEmpty(sale.getSaleOrderList()))
			return;
		for (SaleOrder saleOrder : sale.getSaleOrderList()) {
			if (saleOrder.isNew()) {
				saleOrder.setBoId(saleOrderService.getNextBoId(EntityType.SALEORDER));
			}
		}
	}

	@Transactional(readOnly = false)
	@Override
	public void saveSale(Sale sale) throws ServiceUnavailableException {
		if (sale.isNew()) {
			sale.setBoId(getNextBoId(EntityType.SALE));
			sale.setStatus(Status.OPEN);
			ensuredSaleBoId(sale);
		}
		saleDao.save(sale);
	}

	@Override
	public List<Sale> findByName(String name) throws ServiceUnavailableException {
		String queryStr = "from Sale sale where sale.name=:dataInput and sale.status != :status";
		List<Sale> saleList = saleDao.findByString(queryStr, name);
		if (CollectionUtils.isEmpty(saleList))
			return null;
		hibernateInitializeSaleList(saleList);
		return saleList;
	}

	@Override
	public Sale findByBoId(String boId) throws ServiceUnavailableException {
		return findByBoId(boId, this::hibernateInitializeSale);
	}

	@Override
	public Sale findByBoId(String boId, Consumer<Sale> initializer) {
		String queryStr = "from Sale sale where sale.boId=:dataInput and sale.status != :status";
		List<Sale> saleList = saleDao.findByString(queryStr, boId);
		if (CollectionUtils.isEmpty(saleList))
			return null;
		Sale sale = saleList.get(0);
		initializer.accept(sale);
		return sale;
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
		List<Sale> saleList = saleDao.getActiveObjects("From Sale sale where sale.status!=:status");
		hibernateInitializeSaleList(saleList);
		return saleList;
	}

	@Transactional(readOnly = false)
	public boolean syncWithDb(Sale sale) {
		List<Product> productList = new ArrayList<>();
		for (SaleOrder saleOrder : sale.getSaleOrderList()) {
			Product product = saleOrder.getProduct();
			try {
				product = productService.findByBoId(product.getBoId());
			} catch (ServiceUnavailableException e) {
				
			}
			product.setQuantity(product.getQuantity() - saleOrder.getQuantity());
			if (product.getQuantity() < 0)
				return false;
			productList.add(product);
		}
		return productService.save(productList);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteSale(Sale sale) throws ServiceUnavailableException {
		sale.setStatus(Status.DELETED);
		saveSale(sale);
	}

	public void hibernateInitializeSaleList(List<Sale> saleList) {
		Hibernate.initialize(saleList);
		if (CollectionUtils.isEmpty(saleList))
			return;
		for (Sale sale : saleList) {
			hibernateInitializeSale(sale);
		}
	}

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
		for (Sale sale : saleList) {
			saleOrderList.addAll(sale.getSaleOrderList());
		}
		return saleOrderList;
	}

	private Map<String, Integer> countQuantityByProduct(Map<String, List<SaleOrder>> saleOrderMap) {

		Map<String, Integer> resultMap = new HashMap<>();
		for (List<SaleOrder> list : saleOrderMap.values()) {
			int count = 0;
			for (SaleOrder so : list) {
				count += so.getQuantity();
			}
			resultMap.put(list.get(0).getProduct().getProductName(), count);
		}
		return resultMap;
	}

	private Map<String, List<SaleOrder>> groupSaleOrderByProduct(List<SaleOrder> saleOrderList) {
		Map<String, List<SaleOrder>> saleOrderMap = new HashMap<>();
		for (SaleOrder saleOrder : saleOrderList) {
			List<SaleOrder> list = saleOrderMap.get(saleOrder.getProduct().getBoId());
			if (list == null) {
				list = new ArrayList<>();
				saleOrderMap.put(saleOrder.getProduct().getBoId(), list);
			}
			list.add(saleOrder);
		}
		return saleOrderMap;
	}
}