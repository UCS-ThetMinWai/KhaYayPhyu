package com.khayayphyu.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.PriceDao;
import com.khayayphyu.domain.Price;
import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.PriceService;

@Transactional(readOnly = true)
@Service("priceService")
public class PriceServiceImpl extends AbstractServiceImpl<Price> implements PriceService {
	
	@Autowired
	private PriceDao priceDao;

	@Transactional(readOnly = false)
	@Override
	public void savePrice(Price price) throws ServiceUnavailableException {
		if (price.isBoIdRequired()) {
			price.setBoId(getNextBoId(EntityType.PRICE));
		}
		priceDao.save(price);
	}
	
	@Override
	public List<Price> findByPeriod(Date startDate, Date endDate) throws ServiceUnavailableException {
		String queryString = "from Price price where price.date between :dataInput and :dataInput1";
		List<Price> priceList = priceDao.findByDate(queryString, startDate, endDate);
		return priceList;
	}
	
	@Override
	public List<Price> findByBoId(String boId) throws ServiceUnavailableException {
		String queryStr = "select price from Price price where price.boId=:dataInput";
		List<Price> priceList = priceDao.findByString(queryStr, boId);
		return priceList;
	}

	@Override
	public long getCount() {
		return priceDao.getCount("select count(price) from Price price");
	}

}
