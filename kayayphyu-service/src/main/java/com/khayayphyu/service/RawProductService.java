package com.khayayphyu.service;

import java.util.List;

import com.khayayphyu.domain.RawProduct;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface RawProductService extends AbstractService<RawProduct> {
	public void saveRawProduct(RawProduct rawProduct)throws ServiceUnavailableException;
	public List<RawProduct> getAllRawProduct()throws ServiceUnavailableException;
}
