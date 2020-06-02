package com.khayayphyu.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.SaleOrderDao;
import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.Sale;
import com.khayayphyu.domain.SaleOrder;
import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.ProductService;
import com.khayayphyu.service.SaleOrderService;
import com.khayayphyu.service.SaleService;

@Service("saleOrderService")
@Transactional(readOnly = true)
public class SaleOrderServiceImpl extends AbstractServiceImpl<SaleOrder> implements SaleOrderService {

	@Autowired
	private SaleOrderDao saleOrderDao;

	@Autowired
	private SaleService saleService;

	@Autowired
	private ProductService productService;

	public void ensuredSaleOrderBoId(SaleOrder saleOrder) {
		if (saleOrder.getProduct() == null)
			return;
		Product product = saleOrder.getProduct();
		product.setBoId(productService.getNextBoId(EntityType.PRODUCT));

		if (saleOrder.getSale() == null) {
			return;
		}

		Sale sale = saleOrder.getSale();
		sale.setBoId(saleService.getNextBoId(EntityType.SALE));
	}

	@Transactional(readOnly = false)
	@Override
	public void saveSaleOrder(SaleOrder saleOrder) throws ServiceUnavailableException {
		if (saleOrder.isBoIdRequired()) {
			saleOrder.setBoId(getNextBoId(EntityType.SALEORDER));
			ensuredSaleOrderBoId(saleOrder);
		}
		saleOrderDao.save(saleOrder);
	}

	@Override
	public SaleOrder findByBoId(String boId) throws ServiceUnavailableException {
		String queryStr = "select saleOrder from SaleOrder saleOrder where saleOrder.boId=:dataInput";
		List<SaleOrder> saleOrderList = saleOrderDao.findByString(queryStr, boId);
		if (CollectionUtils.isEmpty(saleOrderList))
			return null;
		hibernateInitializeSaleOrderList(saleOrderList);
		return saleOrderList.get(0);
	}
	
	@Override
	public long getCount() {
		return saleOrderDao.getCount("select count(saleOrder) from SaleOrder saleOrder");
	}

	@Override
	public List<SaleOrder> getAllSaleOrder() throws ServiceUnavailableException {
		List<SaleOrder> saleOrderList = saleOrderDao.getAll("From SaleOrder saleOrder");
		hibernateInitializeSaleOrderList(saleOrderList);
		return saleOrderList;
	}

	@Override
	public void hibernateInitializeSaleOrderList(List<SaleOrder> saleOrderList) {
		Hibernate.initialize(saleOrderList);
		if (CollectionUtils.isEmpty(saleOrderList))
			return;
		for (SaleOrder saleOrder : saleOrderList) {
			hibernateInitializeSaleOrder(saleOrder);
		}
	}

	@Override
	public void hibernateInitializeSaleOrder(SaleOrder saleOrder) {
		Hibernate.initialize(saleOrder);
		if (saleOrder == null)
			return;
		productService.hibernateInitializeProduct(saleOrder.getProduct());
	}

}
