package com.khayayphyu.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.SaleDao;
import com.khayayphyu.domain.Sale;
import com.khayayphyu.domain.SaleOrder;
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
		
		if(CollectionUtils.isEmpty(sale.getSaleOrderList()))
			return;
		for(SaleOrder saleOrder : sale.getSaleOrderList()) {
			if(saleOrder.isBoIdRequired()) {
				saleOrder.setBoId(saleOrderService.getNextBoId(EntityType.SALEORDER));
			}
		}
	}

	@Transactional(readOnly = false)
	@Override
	public void SaveSale(Sale sale) throws ServiceUnavailableException {
		if(sale.isBoIdRequired()) {
			sale.setBoId(getNextBoId(EntityType.SALE));
			ensuredSaleBoId(sale);
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
	public List<Sale> findByBoId(String boId) throws ServiceUnavailableException {
		String queryStr = "select sale from Sale sale where sale.boId=:dataInput";
		List<Sale> saleList = saleDao.findByString(queryStr, boId);
		if (CollectionUtils.isEmpty(saleList))
			return null;
		hibernateInitializeSaleList(saleList);
		return saleList;
	}

	@Override
	public List<Sale> findByPeriod(Date startDate, Date endDate) throws ServiceUnavailableException {
		String queryString = "from Sale sale where sale.saleDate between :dataInput and :dataInput1";
		List<Sale> saleList = saleDao.findByDate(queryString, startDate, endDate);
		return saleList;
	}

	@Override
	public List<Sale> getAllSale() throws ServiceUnavailableException {
		List<Sale> saleList = saleDao.getAll("From Sale sale");
		return saleList;
	}
	
	@Override
	public void hibernateInitializeSaleList(List<Sale> saleList) {
		Hibernate.initialize(saleList);
		if(CollectionUtils.isEmpty(saleList))
			return;
		for(Sale sale : saleList) {
			hibernateInitializeSale(sale);
		}
	}

	@Override
	public void hibernateInitializeSale(Sale sale) {
		Hibernate.initialize(sale);
		if(sale == null)
			return;
		for(SaleOrder saleOrder : sale.getSaleOrderList()) {
			saleOrderService.hibernateInitializeSaleOrder(saleOrder);
		}
	}

	
	@Override
	public long getCount() {
		return saleDao.getCount("select count(sale) from Sale sale");
	}

}
