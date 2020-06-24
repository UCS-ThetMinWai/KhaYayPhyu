package com.khayayphyu.dao.impl;

import java.io.Serializable;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.CustomerDao;
import com.khayayphyu.domain.Customer;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

@Repository
@Transactional(readOnly = true)
public class CustomerDaoImpl extends AbstractDaoImpl<Customer, Serializable> implements CustomerDao {
	
	@Transactional(readOnly = false)
	public void save(Customer customer) throws ServiceUnavailableException {
		saveOrUpdate(customer);
	}
	
}
