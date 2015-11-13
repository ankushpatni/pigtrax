package com.pigtrax.master.controller;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pigtrax.usermanagement.beans.Company;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.service.interfaces.CompanyService;

@Controller
public class PremisesController {
	
	private static final Logger logger = Logger.getLogger(PremisesController.class);

	@Autowired
	CompanyService companyService;
	
	
	@RequestMapping(value = "/loadPremises")
	public String loadPremises(Model model)
	{
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("contentUrl","premises.jsp");
		model.addAttribute("generatedCompanyId",activeUser.getCompanyId());
		try
		{
			Company company = companyService.findByCompanyByAutoGeneratedId(activeUser.getCompanyId());
			if(null != company)
			{
				model.addAttribute("companyId",company.getCompanyId());
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return "template";
	}
	
	@RequestMapping(value = "/premises", method=RequestMethod.POST)
	public String displayPremises(Model model, @RequestParam(value = "generatedCompanyId") int generatedCompanyId)
	{
		model.addAttribute("contentUrl","premises.jsp");
		model.addAttribute("generatedCompanyId",generatedCompanyId);
		try
		{
			Company company = companyService.findByCompanyByAutoGeneratedId(generatedCompanyId);
			if(null != company)
			{
				model.addAttribute("companyId",company.getCompanyId());
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		//logger.info("Getting value from db--->"+premisesService.getCompanyList().size());
		return "template";
	}
	
	@RequestMapping(value = "/addPremises")
	public String addPremises(Model model)
	{
		model.addAttribute("contentUrl","addPremises.jsp"); 
		//logger.info("Getting value from db--->"+premisesService.getCompanyList().size());
		return "addPremises";
	}


}
