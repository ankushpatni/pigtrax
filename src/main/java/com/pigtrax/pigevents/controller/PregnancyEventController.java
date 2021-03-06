package com.pigtrax.pigevents.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pigtrax.usermanagement.beans.Company;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.enums.RoleType;
import com.pigtrax.usermanagement.service.interfaces.CompanyService;

@Controller
public class PregnancyEventController {
	
	 private static final Logger logger = Logger.getLogger(PregnancyEventController.class);
	 
	 @Autowired
	 CompanyService companyService;
	
	   @RequestMapping(value="/pregnancyEvent", method=RequestMethod.POST)
	   public String loadPregnancyEvent(HttpServletRequest request, Model model)  
	   {
		   PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   logger.info("Compayny ID : "+activeUser.getCompanyId()+"/ Role : "+activeUser.getUserRole());
		   String PregnancyEventId = request.getParameter("selectedPregnancyEventId");
		   try{
			   if(request.getParameter("selectedCompany") != null) 
			   {
				   String companyId = request.getParameter("selectedCompany");
				   Company company = companyService.findByCompanyByAutoGeneratedId(Integer.parseInt(companyId));
				   model.addAttribute("CompanyId", Integer.parseInt(companyId));	
				   model.addAttribute("CompanyName", company.getName());
				   model.addAttribute("PregnancyEventId", PregnancyEventId);
			   }
			   else
			   {
				   Company company = companyService.findByCompanyByAutoGeneratedId(activeUser.getCompanyId());
				   model.addAttribute("CompanyId", activeUser.getCompanyId());
				   model.addAttribute("CompanyName", company.getName());
				   model.addAttribute("PregnancyEventId", PregnancyEventId);
			   }
		   }catch(Exception ex)
		   {
			   
		   }
		   model.addAttribute("contentUrl","PregnancyEvent.jsp");
		   return "template";
	   }
	   
	   
	   @RequestMapping("/pigPregnancyEvent")
	   public String goToPregnancyEventPage(HttpServletRequest request, Model model)  
	   {
		   PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   logger.info("Compayny ID : "+activeUser.getCompanyId()+"/ Role : "+activeUser.getUserRole());
		   Locale ln = request.getLocale();
		   logger.info("Locale selected : "+ln.getLanguage());
		   if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue()) 
				   || request.isUserInRole(RoleType.PigTraxDataConfigMgr.getRoleValue())) 
		   {
			   model.addAttribute("contentUrl","company.jsp");
		   }
		   else
		   {
			   try{
				   Company company = companyService.findByCompanyByAutoGeneratedId(activeUser.getCompanyId());
				   model.addAttribute("CompanyId", activeUser.getCompanyId());
				   model.addAttribute("CompanyName", company.getName());
				   
			   }catch(Exception ex)
			   {
				   
			   }
			   model.addAttribute("contentUrl","PregnancyEvent.jsp");
		   }
		   return "template";
	   }
}
