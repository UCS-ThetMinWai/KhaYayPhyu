package com.khayayphyu.resource.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.khayayphyu.domain.RawProduct;
import com.khayayphyu.domain.exception.ServiceUnavailableException;
import com.khayayphyu.resource.RawProductServiceResource;
import com.khayayphyu.service.RawProductService;

@RestController
@RequestMapping(value = "/rawProduct")
public class RawProductServiceResourceImpl extends AbstractServiceResourceImpl implements RawProductServiceResource {

	@Autowired
	private RawProductService rawProductService;
	
	@RequestMapping(method = RequestMethod.POST, value = "")
	@Override
	public Boolean createRawProduct(HttpServletRequest request,@RequestBody RawProduct rawProduct) {
		try {
			rawProductService.saveRawProduct(rawProduct);
		} catch (ServiceUnavailableException e) {
			e.printStackTrace();
		}
		return true;
	}

	@RequestMapping(method = RequestMethod.GET, value = "name/{name}")
	@Override
	public List<RawProduct> findByName(HttpServletRequest request,@PathVariable String name) throws ServiceUnavailableException {
		return rawProductService.findByName(name);
	}

	@RequestMapping(method = RequestMethod.GET, value = "boId/{boId}")
	@Override
	public List<RawProduct> findByBoId(HttpServletRequest request,@PathVariable String boId) throws ServiceUnavailableException {
		return rawProductService.findByBoId(boId);
	}

}
