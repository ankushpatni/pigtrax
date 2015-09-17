package com.pigtrax.master.controller;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pigtrax.usermanagement.beans.PigTraxUser;

@Controller
public class TransportDestinationController {
	
	private static final Logger logger = Logger.getLogger(TransportDestinationController.class);

	@RequestMapping(value = "/transportDestination")
	public String displayTransportTrailerAndTruck(Model model)
	{
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("contentUrl","transportDestination.jsp");
		model.addAttribute("generatedCompanyId",activeUser.getCompanyId());
		return "template";
	}
	
	@RequestMapping(value = "/addTransportDestination")
	public String addTransportDestination(Model model)
	{
		model.addAttribute("contentUrl","addTransportDestination.jsp"); 
		return "addTransportDestination";
	}
	
	@RequestMapping(value = "/transportDestinationWithCompanyId")
	public String displayTransportTrailerAndTruckWithCompanyId(Model model, @RequestParam int generatedCompanyId)
	{
		
		model.addAttribute("contentUrl","transportDestination.jsp");
		model.addAttribute("generatedCompanyId",generatedCompanyId);
		return "template";
	}
	
	
	
}
