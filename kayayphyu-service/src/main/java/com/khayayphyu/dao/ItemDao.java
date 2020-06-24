package com.khayayphyu.dao;

import java.io.Serializable;

import com.khayayphyu.domain.Item;

public interface ItemDao extends AbstractDao<Item, Serializable> {

	public void delete(Item item);
}
