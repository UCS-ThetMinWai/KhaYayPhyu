package com.khayayphyu.resource;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.khayayphyu.domain.SalePrice;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface SalePriceServiceResource {
	Boolean createPrice(HttpServletRequest request, SalePrice price);
	List<SalePrice> findByPeriod(HttpServletRequest request, Date startDate, Date endDate)throws ServiceUnavailableException;
	List<SalePrice> getAllPrice(HttpServletRequest request)throws ServiceUnavailableException;
}
