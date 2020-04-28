package com.khayayphyu.resource.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.khayayphyu.domain.Customer;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.resource.CustomerServiceResource;
import com.khayayphyu.service.CustomerService;

@RestController
@RequestMapping(value = {"customer"})
public class CustomerServiceResourceImpl extends AbstractServiceResourceImpl implements CustomerServiceResource {
	
	private Logger logger = Logger.getLogger(CustomerServiceResourceImpl.class);
	
	@Autowired
	private CustomerService customerService;

	@RequestMapping(method = RequestMethod.POST, value = "")
	@Override
	public Boolean createCustomer(HttpServletRequest request,@RequestBody Customer customer) {
		try {
			customerService.saveCustomer(customer);
			logger.info("Successfully saved!");
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "name/{name}")
	@Override
	public List<Customer> findByCustomerName(HttpServletRequest request,@PathVariable String name)throws ServiceUnavailableException {
		return customerService.findByName(name);
	}

	@RequestMapping(method = RequestMethod.GET, value = "boId/{boId}")
	@Override
	public List<Customer> findByCustomerBoId(HttpServletRequest request,@PathVariable String boId) throws ServiceUnavailableException {
		return customerService.findByBoId(boId);
	}

}
