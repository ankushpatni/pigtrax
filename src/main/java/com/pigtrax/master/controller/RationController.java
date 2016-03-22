package com.pigtrax.master.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RationController {
	
	 private static final Logger logger = Logger.getLogger(RationController.class);
	 
	 
	   @RequestMapping("/ration")
	   public String goToCompanyTarget(HttpServletRequest request, Model model)  
	   { 
		   model.addAttribute("contentUrl","ManageRation.jsp");
		   return "template";
	   }
}
