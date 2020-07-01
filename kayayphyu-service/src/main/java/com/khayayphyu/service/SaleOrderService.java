package com.khayayphyu.service;

import java.util.List;

import com.khayayphyu.domain.Sale;
import com.khayayphyu.domain.SaleOrder;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface SaleOrderService extends AbstractService<SaleOrder> {
	public void saveSaleOrder(SaleOrder saleOrder)throws ServiceUnavailableException;
	public List<SaleOrder> getAllSaleOrder()throws ServiceUnavailableException;
	public void hibernateInitializeSaleOrderList(List<SaleOrder> saleOrderList);
	public void hibernateInitializeSaleOrder(SaleOrder saleOrder);
	public void removeSaleOrderListOf(Sale sale)throws ServiceUnavailableException;
}
