package com.pigtrax.pigevents.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.enums.RoleType;

@Controller
public class EntryEventController {
	
	 private static final Logger logger = Logger.getLogger(EntryEventController.class);
	
	   @RequestMapping(value="/entryEvent", method=RequestMethod.POST)
	   public String loadEntryEnventPage(HttpServletRequest request, Model model)  
	   {
		   PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   logger.info("Compayny ID : "+activeUser.getCompanyId()+"/ Role : "+activeUser.getUserRole());
		   if(request.getParameter("selectedCompany") != null) 
		   {
			   String companyId = request.getParameter("selectedCompany");
			   model.addAttribute("CompanyId", Integer.parseInt(companyId));			   
		   }
		   else
		   {
			   model.addAttribute("CompanyId", activeUser.getCompanyId());
		   }

		   model.addAttribute("contentUrl","PigInfo_EntryEvent.jsp");
		   return "template";
	   }
	   
	   
	   @RequestMapping("/pigEntryEvent")
	   public String goToEntryEventPage(HttpServletRequest request, Model model)  
	   {
		   PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   logger.info("Compayny ID : "+activeUser.getCompanyId()+"/ Role : "+activeUser.getUserRole());
		   LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		   logger.info("Locale from cookie : "+localeResolver.resolveLocale(request).getLanguage());
		   if(request.isUserInRole(String.valueOf(RoleType.PigTraxSuperAdmin.getIntegerValue()))) 
		   {
			   model.addAttribute("contentUrl","company.jsp");
		   }
		   else
		   {
			   model.addAttribute("CompanyId", activeUser.getCompanyId());
			   model.addAttribute("contentUrl","PigInfo_EntryEvent.jsp");
		   }
		   return "template";
	   }
}
