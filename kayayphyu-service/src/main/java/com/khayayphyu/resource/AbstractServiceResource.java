package com.khayayphyu.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface AbstractServiceResource {
	@RequestMapping(value = "/**", method = RequestMethod.OPTIONS)
	public ResponseEntity handle();
}
