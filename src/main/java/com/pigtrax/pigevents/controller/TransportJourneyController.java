package com.pigtrax.pigevents.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pigtrax.pigevents.service.interfaces.TransportJourneyService;

@Controller
public class TransportJourneyController {
	
	private static final Logger logger = Logger.getLogger(PregnancyEventController.class);
	 
	 @Autowired
	 TransportJourneyService transportJourneyService;
	 
	 @RequestMapping(value = "/transportJourney", method=RequestMethod.GET)
		public String transportJourney(HttpServletRequest request,Model model)
		{
			model.addAttribute("contentUrl","transportJourney.jsp"); 
			model.addAttribute("companyId",request.getParameter("companyId"));
			return "transportJourney";
		}	  
}
