package com.pigtrax.usermanagement.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	/**
	 * loadIndexPage loads the login page
	 * @param view
	 * @param model
	 * @return 
	 */
	@RequestMapping(value="/login", method = RequestMethod.GET)
	 public ModelAndView getLoginPage( ModelAndView model, 
		 @RequestParam(value = "error", required = false) String error,
		 @RequestParam(value = "logout", required = false) String logout){
			if (error != null) {
				model.addObject("error", "Invalid username and password!");
			}
		
			if (logout != null) {
				model.addObject("msg", "You've been logged out successfully.");
			}
	 	model.addObject("contentUrl","UserLogin.jsp");
	 	model.setViewName("template");
	 	return model;
	  }
	
	
	/**
	 * Load homepage
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/home", method = RequestMethod.GET)
	public String loadHomePage(HttpServletRequest request, Model model) {
		model.addAttribute("contentUrl","dashboard.jsp");
		return "template"; 
	}
	
		
	/**
	 * logout page invalidates session and loads the login page
	 * @param view
	 * @param model
	 * @return 
	 */
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(true);
		session.invalidate();
		model.addAttribute("contentUrl","UserLogin.jsp");
		return "template"; 
	}	
	
}
