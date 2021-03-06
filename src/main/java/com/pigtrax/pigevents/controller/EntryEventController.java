package com.pigtrax.pigevents.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.pigtrax.usermanagement.beans.Company;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.enums.RoleType;
import com.pigtrax.usermanagement.service.interfaces.CompanyService;

@Controller
public class EntryEventController {
	
	 private static final Logger logger = Logger.getLogger(EntryEventController.class);
	 
	 @Autowired
	 CompanyService companyService;
	 
	
	   @RequestMapping(value="/entryEvent", method=RequestMethod.POST)
	   public String loadEntryEnventPage(HttpServletRequest request, Model model)  
	   {
		   PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		   logger.info("Compayny ID : "+activeUser.getCompanyId()+"/ Role : "+activeUser.getUserRole());
		   try{
			   if(request.getParameter("selectedCompany") != null) 
			   {
				   
				   String companyId = request.getParameter("selectedCompany");
				   Company company = companyService.findByCompanyByAutoGeneratedId(Integer.parseInt(companyId));
				   model.addAttribute("CompanyId", Integer.parseInt(companyId));			   
				   model.addAttribute("CompanyName", company.getName());
				   
				   if(request.getParameter("fromGroup") != null)
				   {
					   String fromGroup = request.getParameter("fromGroup");
					   model.addAttribute("From_Group", fromGroup);
				   }
				   
				   if(request.getParameter("fromGroupId") != null)
				   {
					   String fromGroupId = request.getParameter("fromGroupId");
					   model.addAttribute("From_Group_Id", Integer.parseInt(fromGroupId));
				   }
				   
			   }
			   else
			   {
				   Company company = companyService.findByCompanyByAutoGeneratedId(activeUser.getCompanyId());
				   model.addAttribute("CompanyId", activeUser.getCompanyId());
				   model.addAttribute("CompanyName", company.getName());
			   }
			   
		   }catch(Exception ex)
		   {
			   
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
			   model.addAttribute("contentUrl","PigInfo_EntryEvent.jsp");
		   }
		   return "template";
	   }
}
