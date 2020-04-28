package com.khayayphyu.service;

import java.util.List;

import com.khayayphyu.domain.Customer;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface CustomerService extends AbstractService<Customer> {
	public void saveCustomer(Customer customer)throws ServiceUnavailableException;
	public void hibernateInitializeCustomerList(List<Customer> customerList);
	public void hibernateInitializeCustomer(Customer customer);

}
