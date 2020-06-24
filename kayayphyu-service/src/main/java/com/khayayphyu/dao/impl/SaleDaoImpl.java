package com.khayayphyu.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.khayayphyu.dao.SaleDao;
import com.khayayphyu.domain.Sale;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
@Repository
public class SaleDaoImpl extends AbstractDaoImpl<Sale, Serializable> implements SaleDao {
	
	public void save(Sale sale) throws ServiceUnavailableException {
		saveOrUpdate(sale);
	}

}
