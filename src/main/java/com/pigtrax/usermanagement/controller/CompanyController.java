package com.pigtrax.usermanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pigtrax.usermanagement.service.interfaces.CompanyService;

@Controller
public class CompanyController {
	
	@Autowired
	CompanyService companyService;
	
	@RequestMapping(value = "/company")
	public String displayCompany(Model model)
	{
		model.addAttribute("contentUrl","company.jsp"); 
		System.out.println("Getting value from db--->"+companyService.getCompanyList().size());
		return "template";
	}
	
	@RequestMapping(value = "/addCompany")
	public String addCompany(Model model)
	{
		model.addAttribute("contentUrl","addCompany.jsp"); 
		System.out.println("Getting value from db--->"+companyService.getCompanyList().size());
		return "addCompany";
	}


}
