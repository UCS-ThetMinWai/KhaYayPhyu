package com.service.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.khayayphyu.domain.Customer;
import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.Purchase;
import com.khayayphyu.domain.PurchaseOrder;
import com.khayayphyu.domain.constant.SystemConstant;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.PurchaseOrderService;

public class PurchaseOrderServiceTest extends ServiceTest {
	
	@Autowired
	private PurchaseOrderService purchaseOrderService;
	
	@Test
	public void testSavePurchaseOrder() {
		PurchaseOrder purchaseOrder = new PurchaseOrder();
		purchaseOrder.setBoId(SystemConstant.BOID_REQUIRED);
		purchaseOrder.setWeight(24.5);
		purchaseOrder.setQuantity(40);
		try {
			purchaseOrder.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-13"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Product product = new Product();
		product.setId(2);
		purchaseOrder.setProduct(product);
		
		Customer customer = new Customer();
		customer.setId(1);
		purchaseOrder.setCustomer(customer);
		
		Purchase purchase = new Purchase();
		purchase.setId(1);
		purchaseOrder.setPurchase(purchase);
		
		try {
			purchaseOrderService.savePurchaseOrder(purchaseOrder);
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
		
	}

}
