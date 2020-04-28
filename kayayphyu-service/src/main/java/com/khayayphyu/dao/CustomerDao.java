package com.khayayphyu.dao;

import java.io.Serializable;

import com.khayayphyu.domain.Customer;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface CustomerDao extends AbstractDao<Customer, Serializable> {
	public void save(Customer customer)throws ServiceUnavailableException;
}
