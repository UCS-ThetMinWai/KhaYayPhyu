package com.khayayphyu.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.RootEntityResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.CannotCreateTransactionException;

import com.khayayphyu.dao.AbstractDao;
import com.khayayphyu.domain.constant.Status;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public class AbstractDaoImpl<E, I extends Serializable> implements AbstractDao<E, Serializable> {

	private int pageSize = 30;

	private static Logger logger = Logger.getLogger(AbstractDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public void saveOrUpdate(E e) throws ServiceUnavailableException {
		try {
			Session session = getCurrentSession();
			session.saveOrUpdate(e);
			session.flush();
		} catch (CannotCreateTransactionException exception) {
			logger.error(exception);
			throw new ServiceUnavailableException(exception);
		} catch (Exception exception) {
			logger.error("\r\nException in save : ", exception);
			throw new ServiceUnavailableException(exception);
		}
	}

	public List<E> findByString(String queryString, String data) {
		List<E> entityList;
		Query query = getCurrentSession().createQuery(queryString).setResultTransformer(RootEntityResultTransformer.INSTANCE);
		query.setParameter("dataInput", data);
		query.setParameter("status", Status.DELETED);
		entityList = query.list();
		for (E entity : entityList)
			Hibernate.initialize(entity);
		return entityList;
	}

	public List<E> findByStringWithoutStatus(String queryString, Object data) {
		List<E> entityList;
		Query query = getCurrentSession().createQuery(queryString).setResultTransformer(RootEntityResultTransformer.INSTANCE);
		query.setParameter("dataInput", data);
		entityList = query.list();
		for (E entity : entityList)
			Hibernate.initialize(entity);
		return entityList;
	}

	public List<E> findByStringInteger(String queryString, String data, int data1) {
		List<E> entityList;
		Query query = getCurrentSession().createQuery(queryString).setParameter("dataInput", data).setParameter("dataInput1", data1);
		entityList = query.list();
		for (E entity : entityList)
			Hibernate.initialize(entity);
		return entityList;
	}

	public List<E> findByString(String queryString) {
		List<E> entityList;
		Query query = getCurrentSession().createQuery(queryString);
		entityList = query.list();
		for (E entity : entityList)
			Hibernate.initialize(entity);
		return entityList;
	}

	public List<E> findByString(String queryString, String data, String data1) {
		List<E> entityList;
		Query query = getCurrentSession().createQuery(queryString).setParameter("dataInput", data).setParameter("dataInput1", data1);
		entityList = query.list();
		for (E entity : entityList)
			Hibernate.initialize(entity);
		return entityList;
	}

	public List<E> findByIntegerString(String queryString, Integer data, String data1) {
		List<E> entityList;
		Query query = getCurrentSession().createQuery(queryString).setParameter("dataInput", data).setParameter("dataInput1", data1);
		entityList = query.list();
		for (E entity : entityList)
			Hibernate.initialize(entity);
		return entityList;
	}

	@Override
	public List<E> findByDate(String queryString, Date startDate, Date endDate) {
		//List<E> entityList;
		Query query = getCurrentSession().createQuery(queryString).setParameter("dataInput", startDate).setParameter("dataInput1", endDate);
		List<E> entityList = query.list();
		return entityList == null ? new ArrayList<>() : entityList;
	}

	public long getCount(String queryString) {
		long count;
		Query query = getCurrentSession().createQuery(queryString);
		// query.uniqueResult();
		count = (Long) query.uniqueResult();

		return count;
	}

	public List<E> getActiveObjects(String queryStr) {
		Query query = getCurrentSession().createQuery(queryStr);
		query.setParameter("status", Status.DELETED);
		return query.list();
	}

	public void clearSession() {
		getCurrentSession().clear();
	}

	public List<E> getAll(String queryString) {
		Query query = getCurrentSession().createQuery(queryString);
		List<E> entityList = query.list();

		for (E entity : entityList) {
			Hibernate.initialize(entity);
		}
		return entityList;
	}

	public List<E> getAll(String queryString, int pageNumber) {
		List<E> entityList;
		Query query = getCurrentSession().createQuery(queryString);
		query.setFirstResult(pageSize * (pageNumber - 1));
		query.setMaxResults(pageSize);
		entityList = query.list();
		for (E entity : entityList) {
			Hibernate.initialize(entity);
		}
		return entityList;
	}

}
