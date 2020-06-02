package com.khayayphyu.application.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@RequestMapping(value = "test")
public class BaseController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String hello() {
		return "hello";
	}

	@RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
	public String hello1(@PathVariable("customerId") String customerId) {
		return customerId;
	}

	@RequestMapping(value = "param", method = RequestMethod.GET)
	public String hello2(@RequestParam("customerId") String customerId) {
		return customerId;
	}

	@RequestMapping(value = "header", method = RequestMethod.GET)
	public String hello3(@RequestHeader("customerId") String customerId) {
		return customerId;
	}

	@RequestMapping(value = "body", method = RequestMethod.GET)
	public String hello4(@RequestBody String customerId) {
		return customerId;
	}

}
