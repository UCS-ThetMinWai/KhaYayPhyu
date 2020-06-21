package com.service.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.Sale;
import com.khayayphyu.domain.SaleOrder;
import com.khayayphyu.domain.constant.SystemConstant;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.SaleOrderService;

public class SaleOrderServiceTest extends ServiceTest {
	private Logger logger = Logger.getLogger(SaleOrderServiceTest.class);
	
//	@Autowired
//	private SaleService saleService;
	
	@Autowired
	private SaleOrderService saleOrderService;
	
	@Test
	public void testSaveSaleOrder() {
		SaleOrder saleOrder = new SaleOrder();
		saleOrder.setBoId(SystemConstant.BOID_REQUIRED);
		saleOrder.setWeight(20.5);
		saleOrder.setQuantity(12);
		saleOrder.setAmount(4000);
		
//		Sale sale = new Sale();
//		sale.setBoId(SystemConstant.BOID_REQUIRED);
//		try {
//			sale.setStartDate(new SimpleDateFormat("yyyy-mm-dd").parse("2020-4-23"));
//			sale.setEndDate(new SimpleDateFormat("yyyy-mm-dd").parse("2020-4-13"));
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		Product product = new Product();
		product.setId(1);
		saleOrder.setProduct(product);
		Sale sale = new Sale(); // saleService.find
		sale.setId(2);
		saleOrder.setSale(sale);
		
		try {
			saleOrderService.saveSaleOrder(saleOrder);
			logger.info("saved!");
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
	}
}
