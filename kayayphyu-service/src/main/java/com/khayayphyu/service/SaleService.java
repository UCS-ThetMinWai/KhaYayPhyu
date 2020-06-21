package com.khayayphyu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;

import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.Sale;
import com.khayayphyu.domain.SaleOrder;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface SaleService extends AbstractService<Sale> {
	public List<Sale> findByPeriod(Date stratDate, Date endDate) throws ServiceUnavailableException;

	public List<Sale> getAllSale() throws ServiceUnavailableException;

	public void saveSale(Sale sale) throws ServiceUnavailableException;
	
	public boolean syncWithDb(Sale sale);

	public Map<String, Integer> monthlySaleReport(Date startDate, Date endDate) throws ServiceUnavailableException;

	public void deleteSale(Sale sale) throws ServiceUnavailableException;

	default void detailInitializer(Sale sale) {
		Hibernate.initialize(sale.getSaleBy());
		Hibernate.initialize(sale.getSaleOrderList());
		for (SaleOrder saleOrder : sale.getSaleOrderList()) {
			Hibernate.initialize(saleOrder);
			Hibernate.initialize(saleOrder.getProduct());
			Product product = saleOrder.getProduct();
			product.setProductList(new ArrayList<>());
			product.setSalePrice(null);
		}
	}
}
