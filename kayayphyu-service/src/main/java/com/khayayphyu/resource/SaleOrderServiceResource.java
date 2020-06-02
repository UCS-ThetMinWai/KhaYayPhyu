package com.khayayphyu.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.khayayphyu.domain.SaleOrder;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface SaleOrderServiceResource {
	Boolean createSaleOrder(HttpServletRequest request, SaleOrder saleOrder);
	SaleOrder findBySaleOrderBoId(HttpServletRequest request, String boId)throws ServiceUnavailableException;
	List<SaleOrder> getAllSaleOrder(HttpServletRequest request)throws ServiceUnavailableException;
}
