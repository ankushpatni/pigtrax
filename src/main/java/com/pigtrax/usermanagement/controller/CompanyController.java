package com.pigtrax.usermanagement.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pigtrax.usermanagement.beans.Company;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.service.interfaces.CompanyService;

@Controller
public class CompanyController {
	
	private static final Logger logger = Logger.getLogger(CompanyController.class);

	@Autowired
	CompanyService companyService;
	
	@Secured({"ROLE_1","ROLE_2"})
	@RequestMapping(value = "/company")
	public String displayCompany(Model model)
	{
		model.addAttribute("contentUrl","company.jsp"); 
		logger.info("Getting value from db--->"+companyService.getCompanyList().size());
		return "template";
	}
	
	@Secured({"ROLE_1","ROLE_2"})
	@RequestMapping(value = "/addCompany")
	public String addCompany(Model model)
	{
		model.addAttribute("contentUrl","addCompany.jsp"); 
		logger.info("Getting value from db--->"+companyService.getCompanyList().size());
		return "addCompany";
	}
	
	@RequestMapping(value = "/companyDetail")
	public String companyDetail(HttpServletRequest request, Model model) throws SQLException
	{
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.info("Compayny ID : "+activeUser.getCompanyId()+"/ Role : "+activeUser.getUserRole()); 
		Company company = companyService.findByCompanyByAutoGeneratedId(activeUser.getCompanyId());
		model.addAttribute("CompanyId", company);
		model.addAttribute("contentUrl","companyDetail.jsp"); 
		logger.info("Getting value from db--->"+company);
		return "template";
	}

	@Secured({"ROLE_1"})
	@RequestMapping(value = "/cleanup")
	public String cleanupCompanyData(Model model)
	{
		model.addAttribute("contentUrl","CleanupData.jsp");
		return "template";
	}

}
