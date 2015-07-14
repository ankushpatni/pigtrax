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

@Controller
public class EntryEventController {
	
	 private static final Logger logger = Logger.getLogger(EntryEventController.class);
	
	   @RequestMapping("/entryEvent")
	   public String loadEntryEnventPage(HttpServletRequest request, Model model)  
	   {
		   PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   logger.info("Compayny ID : "+activeUser.getCompanyId());
		   Locale ln = request.getLocale();
		   logger.info("Locale selected : "+ln.getLanguage());
		   if(!request.isUserInRole("1"))
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
