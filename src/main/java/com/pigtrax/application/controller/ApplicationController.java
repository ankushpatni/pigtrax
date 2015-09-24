package com.pigtrax.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApplicationController {
	
	   @RequestMapping("/")
	   public String loadMainPage(Model model)  
	   {
		   model.addAttribute("contentUrl","dashboard.jsp");
		   return "template";
	   }
}
