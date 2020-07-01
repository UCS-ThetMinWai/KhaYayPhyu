package com.khayayphyu.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.SalePriceDao;
import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.SalePrice;
import com.khayayphyu.domain.constant.Status;
import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.SalePriceService;

@Transactional(readOnly = true)
@Service("salePriceService")
public class SalePriceServiceImpl extends AbstractServiceImpl<SalePrice> implements SalePriceService {
	Logger logger = Logger.getLogger(SalePriceServiceImpl.class);
	
	@Autowired
	private SalePriceDao priceDao;

	@Transactional(readOnly = false)
	@Override
	public void savePrice(SalePrice price) throws ServiceUnavailableException {
		if (price.isBoIdRequired()) {
			price.setBoId(getNextBoId(EntityType.PRICE));
			price.setStatus(Status.OPEN);
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
		List<SalePrice> priceList = priceDao.getAll("from SalePrice price and price.status != :status");
		return priceList;
	}

	@Override
	public SalePrice findByProduct(Product product) throws ServiceUnavailableException {
		String queryStr = "from Product product where product.boId=:dataInput";
		//priceDao.findByString(queryStr);
		String test = "select p.salePrice from Product p where p.boId=:dataInput and p.status!=:status";
		//String test ="from SalePrice sp join Product p.id where p.boId=:dataInput and sp.status != :status";
		List<SalePrice> salePriceList = priceDao.findByString(test, product.getBoId());
		logger.info("BoId: " + product.getBoId());
		return salePriceList.get(0);
	}

}
