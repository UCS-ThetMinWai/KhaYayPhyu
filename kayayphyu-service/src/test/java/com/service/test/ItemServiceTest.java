package com.service.test;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.khayayphyu.domain.Product;
import com.khayayphyu.service.ItemService;
import com.khayayphyu.service.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml", "classpath:hibernateContext.xml" })
@PropertySource({ "classpath:log4j.properties", "classpath:application.properties", "classpath:database.properties" })
public class ItemServiceTest extends ServiceTest {

	@Autowired
	private ItemService itemService;

	@Autowired
	private ProductService productService;

	private static Logger logger = Logger.getLogger(ItemServiceTest.class);

	@Ignore
	@Test
	public void updateTest() throws Exception {
		logger.info("here");
		Product product = productService.findByBoId("PRODUCT00000003");
		itemService.update(product, new ArrayList<>());
	}

}
