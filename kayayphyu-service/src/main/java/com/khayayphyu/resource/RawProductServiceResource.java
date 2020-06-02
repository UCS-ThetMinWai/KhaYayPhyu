package com.khayayphyu.resource;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.khayayphyu.domain.RawProduct;
import com.khayayphyu.domain.exception.ServiceUnavailableException;

public interface RawProductServiceResource {
	Boolean createRawProduct(HttpServletRequest request, RawProduct rawProduct);
	List<RawProduct> findByName(HttpServletRequest request, String name)throws ServiceUnavailableException;
	RawProduct findByBoId(HttpServletRequest request, String boId)throws ServiceUnavailableException;
	List<RawProduct> getAllRawProduct(HttpServletRequest request)throws ServiceUnavailableException;
}
