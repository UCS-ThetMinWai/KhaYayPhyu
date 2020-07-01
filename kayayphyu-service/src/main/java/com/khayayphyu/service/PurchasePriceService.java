package com.khayayphyu.service;

import java.util.Date;
import java.util.List;

import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.PurchasePrice;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface PurchasePriceService extends AbstractService<PurchasePrice> {
	public void savePrice(PurchasePrice price)throws ServiceUnavailableException;
	public List<PurchasePrice> findByPeriod(Date stratDate, Date endDate)throws ServiceUnavailableException;
	public List<PurchasePrice> getAllPrice()throws ServiceUnavailableException;
	public PurchasePrice findByProduct(Product product) throws ServiceUnavailableException;
}
