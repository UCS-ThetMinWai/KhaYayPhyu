package com.khayayphyu.service.resource.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
	
	@ResponseBody
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String loginScreen(Model model, HttpServletRequest http) throws Exception {
		return "Success";
	}
}
