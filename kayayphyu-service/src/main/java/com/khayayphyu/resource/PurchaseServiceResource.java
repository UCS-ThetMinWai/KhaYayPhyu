package com.khayayphyu.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.khayayphyu.domain.Purchase;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface PurchaseServiceResource {
	Boolean createPurchase(HttpServletRequest request, Purchase purchase);
	List<Purchase> findByPurchaseBoId(HttpServletRequest request, String boId)throws ServiceUnavailableException;
	List<Purchase> findByName(HttpServletRequest request, String name)throws ServiceUnavailableException;
	List<Purchase> getAllPurchase(HttpServletRequest request)throws ServiceUnavailableException;
}
