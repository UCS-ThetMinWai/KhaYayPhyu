package com.khayayphyu.service;

import java.util.Date;
import java.util.List;

import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.SalePrice;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface SalePriceService extends AbstractService<SalePrice> {
	public void savePrice(SalePrice price)throws ServiceUnavailableException;
	public List<SalePrice> findByPeriod(Date stratDate, Date endDate)throws ServiceUnavailableException;
	public List<SalePrice> getAllPrice()throws ServiceUnavailableException;
	public SalePrice findByProduct(Product product)throws ServiceUnavailableException;
}
