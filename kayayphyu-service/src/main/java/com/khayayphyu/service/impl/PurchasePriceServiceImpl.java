package com.khayayphyu.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.PurchasePriceDao;
import com.khayayphyu.domain.PurchasePrice;
import com.khayayphyu.domain.constant.Status;
import com.khayayphyu.domain.constant.SystemConstant;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.PurchasePriceService;

@Transactional(readOnly = true)
@Service("purchasePriceService")
public class PurchasePriceServiceImpl extends AbstractServiceImpl<PurchasePrice> implements PurchasePriceService {
	PurchasePriceDao purchasePriceDao;

	@Transactional(readOnly = false)
	@Override
	public void savePrice(PurchasePrice price) throws ServiceUnavailableException {
		if (price.isNew()) {
			price.setBoId(SystemConstant.BOID_REQUIRED);
			price.setStatus(Status.OPEN);
		}
		purchasePriceDao.save(price);
	}

	@Override
	public PurchasePrice findByBoId(String boId) throws ServiceUnavailableException {
		String queryStr = "select price from SalePrice price where price.boId=:dataInput";
		List<PurchasePrice> priceList = purchasePriceDao.findByString(queryStr, boId);
		return priceList.get(0);
	}

	@Override
	public List<PurchasePrice> findByPeriod(Date startDate, Date endDate) throws ServiceUnavailableException {
		String queryString = "from PurchasePrice price where price.date between :dataInput and :dataInput1";
		List<PurchasePrice> priceList = purchasePriceDao.findByDate(queryString, startDate, endDate);
		return priceList;
	}

	@Override
	public List<PurchasePrice> getAllPrice() throws ServiceUnavailableException {
		List<PurchasePrice> priceList = purchasePriceDao.getAll("from SalePrice price");
		return priceList;
	}

	@Override
	public long getCount() {
		return purchasePriceDao.getCount("select count(price) from PurchasePrice price");
	}

}
