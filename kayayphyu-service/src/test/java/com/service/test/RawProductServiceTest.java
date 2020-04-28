package com.service.test;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.khayayphyu.domain.RawProduct;
import com.khayayphyu.domain.constant.SystemConstant;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.RawProductService;

public class RawProductServiceTest extends ServiceTest {
	private Logger logger = Logger.getLogger(RawProductServiceTest.class);
	
	@Autowired
	private RawProductService rawProductService;
	
	@Test
	public void testSaveRawProduct() {
		RawProduct rawProduct = new RawProduct();
		rawProduct.setBoId(SystemConstant.BOID_REQUIRED);
		rawProduct.setProductName("FOOD");
		rawProduct.setProductType("CHICKEN FOOD");
		
		try {
			rawProductService.saveRawProduct(rawProduct);
			logger.info("saved!");
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
	}
}
