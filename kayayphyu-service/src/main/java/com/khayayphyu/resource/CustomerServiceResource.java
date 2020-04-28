package com.khayayphyu.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.khayayphyu.domain.Customer;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface CustomerServiceResource {
	Boolean createCustomer(HttpServletRequest request, Customer customer);
	List<Customer> findByCustomerName(HttpServletRequest request, String name)throws ServiceUnavailableException;
	List<Customer> findByCustomerBoId(HttpServletRequest request, String boId)throws ServiceUnavailableException;
}
