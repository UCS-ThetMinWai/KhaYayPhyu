package com.service.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.khayayphyu.domain.SalePrice;
import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.constant.SystemConstant;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.SalePriceService;

public class PriceServiceTest extends ServiceTest {
	Logger logger = Logger.getLogger(PriceServiceTest.class);

	@Autowired
	private SalePriceService priceService;

	@Test
	public void testSavePrice() {
		SalePrice price = new SalePrice();
		price.setBoId(SystemConstant.BOID_REQUIRED);
		price.setAmount(400000);
		price.setDiscount(0);
		try {
			price.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-03-18"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		Product product = new Product();
		product.setId(1);
		price.setProduct(product);
		try {
			priceService.savePrice(price);
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test1() throws Exception {
		Product product = new Product();
		product.setBoId("PRODUCT00000001");
		SalePrice salePrice = priceService.findByProduct(product);
		logger.info("Sale price id :" + salePrice.getId());
	}
}
