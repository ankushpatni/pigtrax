package com.pigtrax.usermanagement.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pigtrax.usermanagement.dao.interfaces.EmployeeDao;
import com.pigtrax.usermanagement.service.interfaces.CompanyService;
import com.pigtrax.usermanagement.service.interfaces.EmployeeService;

@Controller
public class EmployeeController {
	
	@Autowired
	CompanyService companyService;
	@Autowired
	private HttpSession session;
	@Autowired
	private EmployeeService employeeservice;
	@Autowired
	private HttpServletRequest request;
	
	private static final Logger logger = Logger.getLogger(EmployeeController.class);
	@RequestMapping(value = "/employee")
	public String displayEmployeePage(Model model)
	{
		model.addAttribute("contentUrl","employee.jsp"); 
		return "template";
	} 
    
	@RequestMapping(value = "/addEmployee")
	public String addEmployee(Model model)
	{	
		return "addEmployee";
	}
	@RequestMapping(value="/editEmployee")
	public String editEmployee(@RequestParam(value = "empId") int empId , Model model){
		logger.info("edit employee --> "+empId);		
		model.addAttribute("employeeId", empId);
		model.addAttribute("editEmployee.jsp");
		return "editEmployee";
	}
	
}
