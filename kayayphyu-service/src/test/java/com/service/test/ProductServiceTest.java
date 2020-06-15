package com.service.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.khayayphyu.domain.SalePrice;
import com.khayayphyu.domain.Product;
import com.khayayphyu.domain.constant.PackingType;
import com.khayayphyu.domain.constant.SystemConstant;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.service.ProductService;

public class ProductServiceTest extends ServiceTest {
	Logger logger = Logger.getLogger(ProductServiceTest.class);
	
	@Autowired
	private ProductService productService;
	
	@Test
	public void testSaveProduct() {
		Product product = new Product();
		product.setPriceList(new ArrayList<SalePrice>());
		product.setBoId(SystemConstant.BOID_REQUIRED);
		product.setProductName("CP CHICKEN FOOD");
		product.setQuantity(40);
		product.setPackagingType(PackingType.PACK);
		try {
			product.setPackagingDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-13"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		SalePrice price = new SalePrice();
		price.setBoId(SystemConstant.BOID_REQUIRED);
		price.setAmount(620000);
		price.setDiscount(20);
		try {
			price.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2020-4-18"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		product.setSalePrice(price);
		product.getPriceList().add(price);
		
		try {
			productService.saveProduct(product);
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
	}
}
