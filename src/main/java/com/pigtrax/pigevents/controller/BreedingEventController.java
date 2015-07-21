package com.pigtrax.pigevents.controller;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.enums.RoleType;

@Controller
public class BreedingEventController {
	
	 private static final Logger logger = Logger.getLogger(BreedingEventController.class);
	
	   @RequestMapping("/breedingEvent")
	   public String loadBreedingEvent(HttpServletRequest request, Model model)  
	   {
		   PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   logger.info("Compayny ID : "+activeUser.getCompanyId()+"/ Role : "+activeUser.getUserRole());
		   if(request.getParameter("companyId") != null) 
		   {
			   String companyId = request.getParameter("companyId");
			   model.addAttribute("CompanyId", Integer.parseInt(companyId));			   
		   }
		   else
		   {
			   model.addAttribute("CompanyId", activeUser.getCompanyId());
		   }

		   model.addAttribute("contentUrl","BreedingEvent.jsp");
		   return "template";
	   }
	   
	   
	   @RequestMapping("/pigBreedingEvent")
	   public String goToEntryEventPage(HttpServletRequest request, Model model)  
	   {
		   PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   logger.info("Compayny ID : "+activeUser.getCompanyId()+"/ Role : "+activeUser.getUserRole());
		   Locale ln = request.getLocale();
		   logger.info("Locale selected : "+ln.getLanguage());
		   if(request.isUserInRole(String.valueOf(RoleType.PigTraxSuperAdmin.getIntegerValue()))) 
		   {
			   model.addAttribute("contentUrl","company.jsp");
		   }
		   else
		   {
			   model.addAttribute("CompanyId", activeUser.getCompanyId());
			   model.addAttribute("contentUrl","BreedingEvent.jsp");
		   }
		   return "template";
	   }
}
