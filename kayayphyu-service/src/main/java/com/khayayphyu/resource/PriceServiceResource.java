package com.khayayphyu.resource;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.khayayphyu.domain.Price;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface PriceServiceResource {
	Boolean createPrice(HttpServletRequest request, Price price);
	List<Price> findByPeriod(HttpServletRequest request, Date startDate, Date endDate)throws ServiceUnavailableException;
	List<Price> getAllPrice(HttpServletRequest request)throws ServiceUnavailableException;
}
