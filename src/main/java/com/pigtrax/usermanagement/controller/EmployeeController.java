package com.pigtrax.usermanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EmployeeController {
	
	@RequestMapping(value = "/employee")
	public String displayEmployeePage(Model model)
	{
		model.addAttribute("contentUrl","employee.jsp"); 
		return "template";
	}
    
}
