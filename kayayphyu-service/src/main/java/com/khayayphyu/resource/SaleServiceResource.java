package com.khayayphyu.resource;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.khayayphyu.domain.Sale;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface SaleServiceResource {
	boolean createSale(Sale sale) throws ServiceUnavailableException;
	boolean deleteSale(String boId) throws ServiceUnavailableException;
	Sale findBySaleBoId(HttpServletRequest request, String boId)throws ServiceUnavailableException;
	List<Sale> findByName(HttpServletRequest request, String name)throws ServiceUnavailableException;
	List<Sale> findByPeriod(HttpServletRequest request, Date startDate, Date endDate)throws ServiceUnavailableException;
	List<Sale> getAllSale(HttpServletRequest request)throws ServiceUnavailableException;
}
