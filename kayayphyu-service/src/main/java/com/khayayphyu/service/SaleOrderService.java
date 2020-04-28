package com.khayayphyu.service;

import java.util.List;

import com.khayayphyu.domain.SaleOrder;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface SaleOrderService extends AbstractService<SaleOrder> {
	public void saveSaleOrder(SaleOrder saleOrder)throws ServiceUnavailableException;
	public void hibernateInitializeSaleOrderList(List<SaleOrder> saleOrderList);
	public void hibernateInitializeSaleOrder(SaleOrder saleOrder);
}
