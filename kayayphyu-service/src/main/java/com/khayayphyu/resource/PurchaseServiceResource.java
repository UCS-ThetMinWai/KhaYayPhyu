package com.khayayphyu.resource;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.khayayphyu.domain.Purchase;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface PurchaseServiceResource {
	Boolean createPurchase(HttpServletRequest request, Purchase purchase)throws ServiceUnavailableException;
	boolean deletePurchase(String boId)throws ServiceUnavailableException;
	Purchase findByPurchaseBoId(HttpServletRequest request, String boId)throws ServiceUnavailableException;
	List<Purchase> findByPeriod(HttpServletRequest request, Date startDate, Date endDate)throws ServiceUnavailableException;
	List<Purchase> findByName(HttpServletRequest request, String name)throws ServiceUnavailableException;
	List<Purchase> getAllPurchase(HttpServletRequest request)throws ServiceUnavailableException;
}
