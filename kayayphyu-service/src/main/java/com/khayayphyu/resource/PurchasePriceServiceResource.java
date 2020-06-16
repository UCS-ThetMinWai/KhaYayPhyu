package com.khayayphyu.resource;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.khayayphyu.domain.PurchasePrice;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface PurchasePriceServiceResource {
	boolean createPrice(HttpServletRequest request, PurchasePrice price);
	List<PurchasePrice> findByPeriod(HttpServletRequest request, Date startDate, Date endDate)throws ServiceUnavailableException;
	List<PurchasePrice> getAllPrice(HttpServletRequest request)throws ServiceUnavailableException;
}
