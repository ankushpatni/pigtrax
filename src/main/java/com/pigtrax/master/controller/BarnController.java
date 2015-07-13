package com.pigtrax.master.controller;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.dto.Premises;
import com.pigtrax.master.service.interfaces.PremisesService;

@Controller
public class BarnController {
	
	private static final Logger logger = Logger.getLogger(BarnController.class);

	@Autowired
	PremisesService premisesService;
	
	@Autowired
	RefDataCache refDataCache;
	
	@RequestMapping(value = "/barn")
	public String displayBarn(Model model, @RequestParam int generatedPremisesId)
	{
		model.addAttribute("contentUrl","barn.jsp");
		model.addAttribute("generatedPremisesId",generatedPremisesId);
		try
		{
			Premises premises = premisesService.findByPremisesByAutoGeneratedId(generatedPremisesId);
			if(null != premises)
			{
				model.addAttribute("premisesId",premises.getPermiseId());				
			}
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return "template";
	}
	
	@RequestMapping(value = "/addBarn")
	public String addPremises(Model model)
	{
		model.addAttribute("contentUrl","addBarn.jsp"); 
		return "addBarn";
	}

}
