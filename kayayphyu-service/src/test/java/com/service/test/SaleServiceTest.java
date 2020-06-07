package com.service.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.khayayphyu.domain.Customer;
import com.khayayphyu.domain.Sale;
import com.khayayphyu.domain.SaleOrder;
import com.khayayphyu.domain.User;
import com.khayayphyu.domain.constant.PackingType;
import com.khayayphyu.domain.constant.SystemConstant;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.SaleService;

public class SaleServiceTest extends ServiceTest {
	private Logger logger = Logger.getLogger(SaleServiceTest.class);
	
	@Autowired
	private SaleService saleService;
	
	@Test
	public void testSaveSale() {
		Sale sale = new Sale();
		sale.setBoId(SystemConstant.BOID_REQUIRED);
		sale.setPayAmount(40000);
		sale.setTotal(50000);
		sale.getBalance();
		sale.setSaleOrderList(new ArrayList<SaleOrder>());
		try {
			sale.setSaleDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-13"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		SaleOrder saleOrder = new SaleOrder();
		saleOrder.setBoId(SystemConstant.BOID_REQUIRED);
		saleOrder.setPeckagingType(PackingType.CARD);
		saleOrder.setSale(sale);
		sale.getSaleOrderList().add(saleOrder);
		
		Customer customer = new Customer();
		customer.setId(1);
		sale.setCustomer(customer);
		
		User user = new User();
		user.setId(1);
		sale.setSaleBy(user);
		
		try {
			saleService.saveSale(sale);
			logger.info("balance " + sale.getBalance());
			logger.info("save!");
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
	}
}
