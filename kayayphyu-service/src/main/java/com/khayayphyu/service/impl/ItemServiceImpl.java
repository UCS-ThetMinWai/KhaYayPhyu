package com.khayayphyu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.khayayphyu.dao.ItemDao;
import com.khayayphyu.domain.Item;
import com.khayayphyu.domain.Product;
import com.khayayphyu.service.ItemService;

@Service
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemDao itemDao;

	private static Logger logger = Logger.getLogger(ItemServiceImpl.class);

	@Transactional(readOnly = false)
	public boolean update(Product product, List<Item> newList) {
		logger.info("product id :" + product.getId());
		String query = "from Item item where item.parent.boId=:dataInput and item.status!=:status";
		List<Item> itemFromDb = itemDao.findByString(query, product.getBoId());
		Map<String, Item> itemMap = new HashMap<>();
		newList.forEach(item -> {
			itemMap.put(item.getId() + "", item);
		});
		logger.info("current :" + newList.size());
		logger.info("previous :" + itemFromDb.size());
		for (Item item : itemFromDb) {
			if (itemMap.containsKey(item.getId() + ""))
				continue;
			itemDao.delete(item);
		}
		itemFromDb.forEach(item -> logger.info(item.getId()));
		return false;
	}

	@Transactional(readOnly = false)
	public void removeItemListOf(Product product) {
		List<Item> itemList = itemDao.findByStringWithoutStatus("from Item item where item.parent=:dataInput", product);
		for (Item item : itemList) {
			itemDao.delete(item);
		}
	}

}
