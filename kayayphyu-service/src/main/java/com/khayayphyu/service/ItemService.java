package com.khayayphyu.service;

import java.util.List;

import com.khayayphyu.domain.Item;
import com.khayayphyu.domain.Product;

public interface ItemService {
	boolean update(Product product, List<Item> newList);

	public void removeItemListOf(Product product);
}
