package com.pigtrax.master.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OriginController {
	
	 private static final Logger logger = Logger.getLogger(OriginController.class);
	 
	 
	   @RequestMapping("/origin")
	   public String goToCompanyTarget(HttpServletRequest request, Model model)  
	   { 
		   model.addAttribute("contentUrl","ManageOrigin.jsp");
		   return "template";
	   }
}
