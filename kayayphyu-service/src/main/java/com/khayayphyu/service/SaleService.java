package com.khayayphyu.service;

import java.util.Date;
import java.util.List;

import com.khayayphyu.domain.Sale;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface SaleService extends AbstractService<Sale> {
	public List<Sale> findByPeriod(Date stratDate, Date endDate)throws ServiceUnavailableException;
	public List<Sale> getAllSale()throws ServiceUnavailableException;
	public void SaveSale(Sale sale)throws ServiceUnavailableException;
	public void hibernateInitializeSaleList(List<Sale> saleList);
	public void hibernateInitializeSale(Sale sale);
}
