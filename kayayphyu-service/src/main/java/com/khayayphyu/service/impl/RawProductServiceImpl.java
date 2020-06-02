package com.khayayphyu.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.RawProductDao;
import com.khayayphyu.domain.RawProduct;
import com.khayayphyu.domain.constant.Status;
import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.RawProductService;

@Service("rawProductService")
@Transactional(readOnly = true)
public class RawProductServiceImpl extends AbstractServiceImpl<RawProduct> implements RawProductService {
	
	@Autowired
	private RawProductDao rawProductDao;

	@Override
	@Transactional(readOnly = false)
	public void saveRawProduct(RawProduct rawProduct) throws ServiceUnavailableException {
		if(rawProduct.isBoIdRequired()) {
			rawProduct.setBoId(getNextBoId(EntityType.RAWPRODUCT));
			rawProduct.setStatus(Status.OPEN);
		}
		rawProductDao.save(rawProduct);
	}
	
	@Override
	public List<RawProduct> findByName(String name) throws ServiceUnavailableException {
		String queryStr = "from RawProduct rawProduct where rawProduct.name like :dataInput and rawProduct.status != :status";
		List<RawProduct> rawProductList = rawProductDao.findByString(queryStr, name);
		return rawProductList;
	}
	
	@Override
	public RawProduct findByBoId(String boId) throws ServiceUnavailableException {
		String queryStr = "select rawProduct from RawProduct rawProduct where rawProduct.boId=:dataInput and rawProduct.status != :status";
		List<RawProduct> rawProductList = rawProductDao.findByString(queryStr, boId);
		return rawProductList.get(0);
	}

	@Override
	public long getCount() {
		return rawProductDao.getCount("select count(rawProduct) from RawProduct rawProduct");
	}

	@Override
	public List<RawProduct> getAllRawProduct() throws ServiceUnavailableException {
		List<RawProduct> rawProductList = rawProductDao.getAll("From RawProduct rawProduct");
		return rawProductList;
	}

}
