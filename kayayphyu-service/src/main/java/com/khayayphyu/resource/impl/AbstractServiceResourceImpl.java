package com.khayayphyu.resource.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.khayayphyu.resource.AbstractServiceResource;

public class AbstractServiceResourceImpl implements AbstractServiceResource {
	private static final Logger logger = Logger.getLogger(AbstractServiceResourceImpl.class);

	public AbstractServiceResourceImpl() {

	}

	@RequestMapping(value = "/**", method = RequestMethod.OPTIONS)

	public ResponseEntity handle() {
		return new ResponseEntity(HttpStatus.OK);
	}

	protected Date convertToDate(String dateStr, String format) {

		DateFormat df = new SimpleDateFormat(format);
		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			logger.error(e);
		}
		return null;
	}
}
