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

import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.dto.Barn;
import com.pigtrax.master.service.interfaces.TransportTrailerService;
import com.pigtrax.master.service.interfaces.TransportTruckService;
import com.pigtrax.usermanagement.beans.PigTraxUser;

@Controller
public class TransportTrailerTruckController {
	
	private static final Logger logger = Logger.getLogger(TransportTrailerTruckController.class);

	@Autowired
	TransportTruckService transportTruckServiceImpl;
	
	@Autowired
	TransportTrailerService transportTrailerServiceImpl;
	
	@Autowired
	RefDataCache refDataCache;
	
	@RequestMapping(value = "/transportTrailerAndTruck")
	public String displayTransportTrailerAndTruck(Model model)
	{
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		model.addAttribute("contentUrl","transportTrailerTruck.jsp");
		model.addAttribute("generatedCompanyId",activeUser.getCompanyId());
		return "template";
	}
	
	@RequestMapping(value = "/addTruck")
	public String addTruck(Model model)
	{
		model.addAttribute("contentUrl","addTruck.jsp"); 
		return "addTruck";
	}
	

}
