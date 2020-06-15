package com.khayayphyu.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.PriceDao;
import com.khayayphyu.domain.SalePrice;
import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.PriceService;

@Transactional(readOnly = true)
@Service("priceService")
public class PriceServiceImpl extends AbstractServiceImpl<SalePrice> implements PriceService {
	
	@Autowired
	private PriceDao priceDao;

	@Transactional(readOnly = false)
	@Override
	public void savePrice(SalePrice price) throws ServiceUnavailableException {
		if (price.isBoIdRequired()) {
			price.setBoId(getNextBoId(EntityType.PRICE));
		}
		priceDao.save(price);
	}
	
	@Override
	public List<SalePrice> findByPeriod(Date startDate, Date endDate) throws ServiceUnavailableException {
		String queryString = "from SalePrice price where price.date between :dataInput and :dataInput1";
		List<SalePrice> priceList = priceDao.findByDate(queryString, startDate, endDate);
		return priceList;
	}
	
	@Override
	public SalePrice findByBoId(String boId) throws ServiceUnavailableException {
		String queryStr = "select price from SalePrice price where price.boId=:dataInput";
		List<SalePrice> priceList = priceDao.findByString(queryStr, boId);
		return priceList.get(0);
	}

	@Override
	public long getCount() {
		return priceDao.getCount("select count(price) from SalePrice price");
	}

	@Override
	public List<SalePrice> getAllPrice() throws ServiceUnavailableException {
		List<SalePrice> priceList = priceDao.getAll("from SalePrice price");
		return priceList;
	}

}
