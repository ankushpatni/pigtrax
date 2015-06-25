package com.pigtrax.usermanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CompanyController {
	
	@RequestMapping(value = "/company")
	public String displayCompany(Model model)
	{
		model.addAttribute("contentUrl","company.jsp"); 
		return "template";
	}

}
