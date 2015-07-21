package com.pigtrax.master.controller;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pigtrax.usermanagement.beans.Company;
import com.pigtrax.usermanagement.service.interfaces.CompanyService;

@Controller
public class PremisesController {
	
	private static final Logger logger = Logger.getLogger(PremisesController.class);

	@Autowired
	CompanyService companyService;
	
	@RequestMapping(value = "/premises")
	public String displayPremises(Model model, @RequestParam int generatedCompanyId)
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