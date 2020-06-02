package com.service.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.khayayphyu.domain.Customer;
import com.khayayphyu.domain.Purchase;
import com.khayayphyu.domain.PurchaseOrder;
import com.khayayphyu.domain.User;
import com.khayayphyu.domain.constant.Status;
import com.khayayphyu.domain.constant.SystemConstant;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.PurchaseService;

public class PurchaseServiceTest extends ServiceTest {
	private Logger logger = Logger.getLogger(PurchaseServiceTest.class);
	
	@Autowired
	private PurchaseService purchaseService;
	
	@Test
	public void testGetAllPurchase() {
		try {
			List<Purchase> purchaseList = purchaseService.getAllPurchase();
			logger.info("total found purchase list :" + purchaseList.size());
			for(Purchase purchase : purchaseList) {
				logger.info(purchase);
			}
		}catch (ServiceUnavailableException e) {
			logger.error(e);
		}
	}
	
	@Test
	public void testSavePurchase() {
		Purchase purchase = new Purchase();
		purchase.setPurchaseOrderList(new ArrayList<PurchaseOrder>());
		purchase.setBoId(SystemConstant.BOID_REQUIRED);
		purchase.setPayAmount(350000);
		purchase.setTotal(400000);
		purchase.setStatus(Status.OPEN);
		logger.info("purchase boId" + purchase.getBoId());
		try {
			purchase.setPurchaseDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-04-11"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Customer customer = new Customer();
		customer.setId(1);
		purchase.setCustomer(customer);
		logger.info("Customer boId" + customer.getBoId());
		
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		logger.info("in purchase order");
		purchaseOrder.setBoId(SystemConstant.BOID_REQUIRED);
		purchaseOrder.setQuantity(20);
		try {
			purchaseOrder.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-04-15"));
			purchase.getPurchaseOrderList().add(purchaseOrder);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		User user = new User();
		user.setId(1);
		purchase.setUser(user);
		try {
			purchaseService.savePurchase(purchase);
			logger.info("save successful!");
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testFindByPeriod() {
		List<Purchase> purchaseList = null;
		try {	
			try {
				purchaseList = purchaseService.findByPeriod(new SimpleDateFormat("yyyy-MM-dd").parse("2020-01-11"), new SimpleDateFormat("yyyy-mm-dd").parse("2020-01-12"));
			} catch (ParseException e) {
				e.printStackTrace();
			}

		} catch (ServiceUnavailableException e) {
			logger.info("Error is:::" + e);

		}
		
		for(Purchase purchase : purchaseList) {
			
			logger.info(purchase);
		}
	}
	
}
