package com.khayayphyu.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface AbstractDao<E, I extends Serializable> {

	public void saveOrUpdate(E e) throws ServiceUnavailableException;

	public List<E> findByString(String queryString, String data);

	public List<E> findByStringInteger(String queryString, String data, int data1);
	
	public List<E> findByDate(String queryString, Date startDate, Date endDate);

	public List<E> findByString(String queryString);

	public List<E> findByString(String queryString, String data, String data1);

	public List<E> findByIntegerString(String queryString, Integer data, String data1);

	public long getCount(String queryString);

	public List<E> getAll(String queryString);

	public List<E> getAll(String queryString, int pageNumber);
	
	default public List<E> getActiveObjects(String queryStr) {
		return new ArrayList<>();
	}
	
	default public void clearSession() {
		
	}
}