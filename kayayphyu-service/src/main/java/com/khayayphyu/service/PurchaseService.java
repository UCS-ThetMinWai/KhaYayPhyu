package com.khayayphyu.service;

import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;

import com.khayayphyu.domain.Purchase;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface PurchaseService extends AbstractService<Purchase> {
	public List<Purchase> findByPeriod(Date stratDate, Date endDate) throws ServiceUnavailableException;

	public void deletePurchase(Purchase purchase) throws ServiceUnavailableException;

	public List<Purchase> getAllPurchase() throws ServiceUnavailableException;

	public void savePurchase(Purchase purchase) throws ServiceUnavailableException;

	public void hibernateInitializePurchaseList(List<Purchase> purchaseList);

	public void hibernateInitializePurchase(Purchase purchase);

}
