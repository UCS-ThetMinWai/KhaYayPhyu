package com.khayayphyu.service.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.CustomerDao;
import com.khayayphyu.domain.Customer;
import com.khayayphyu.domain.constant.Status;
import com.khayayphyu.domain.constant.SystemConstant.EntityType;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.CustomerService;

@Service("customerService")
@Transactional(readOnly = true)
public class CustomerServiceImpl extends AbstractServiceImpl<Customer> implements CustomerService {
	
	private Logger logger = Logger.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerDao customerDao;
	
	@Transactional(readOnly = false)
	@Override
	public void saveCustomer(Customer customer) throws ServiceUnavailableException {
		if (customer.isBoIdRequired()) {
			customer.setBoId(getNextBoId(EntityType.CUSTOMER));
			customer.setStatus(Status.ACTIVE);
		}
		customerDao.save(customer);
	}

	@Override
	public long getCount() {
		return customerDao.getCount("select count(customer)from Customer customer");
	}

	@Override
	public List<Customer> findByName(String name) throws ServiceUnavailableException {
		String queryStr = "from Customer customer where customer.name like :dataInput and customer.status != :status";
		List<Customer> customerList = customerDao.findByString(queryStr, "%" + name + "%");
		logger.info(customerList);
		if (CollectionUtils.isEmpty(customerList))
			return null;
		 hibernateInitializeCustomerList(customerList);
		return customerList;
	}
	
	@Override
	public Customer findByBoId(String boId) throws ServiceUnavailableException {
		String queryStr = "from Customer customer where customer.boId=:dataInput and customer.status != :status";
		List<Customer> customerList = customerDao.findByString(queryStr, boId);
		if (CollectionUtils.isEmpty(customerList))
			return null;
		hibernateInitializeCustomerList(customerList);
		return customerList.get(0);
	}
	
	@Transactional(readOnly = false)
	@Override
	public void deleteCustmer(Customer customer) throws ServiceUnavailableException {
		customer.setStatus(Status.DELETED);
		saveCustomer(customer);
	}

	@Override
	public void hibernateInitializeCustomerList(List<Customer> customerList) {
		Hibernate.initialize(customerList);
		if (CollectionUtils.isEmpty(customerList))
			return;

		for (Customer customer : customerList) {
			hibernateInitializeCustomer(customer);
		}
	}

	@Override
	public void hibernateInitializeCustomer(Customer customer) {
		Hibernate.initialize(customer);
		if(customer == null) {
			return;
		}
	}

	@Override
	public List<Customer> getAllCustomer() throws ServiceUnavailableException {
		List<Customer> customerList = customerDao.getAll("From Customer customer");
		return customerList;
	}

}
