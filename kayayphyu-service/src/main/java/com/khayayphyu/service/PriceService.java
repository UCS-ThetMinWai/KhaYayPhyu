package com.khayayphyu.service;

import java.util.Date;
import java.util.List;

import com.khayayphyu.domain.Price;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface PriceService extends AbstractService<Price> {
	public void savePrice(Price price)throws ServiceUnavailableException;
	public List<Price> findByPeriod(Date stratDate, Date endDate)throws ServiceUnavailableException;
}
