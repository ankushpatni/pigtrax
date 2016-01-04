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

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.service.interfaces.GroupEventService;
import com.pigtrax.usermanagement.beans.Company;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.enums.RoleType;
import com.pigtrax.usermanagement.service.interfaces.CompanyService;

@Controller
public class GroupEventController {
	
 private static final Logger logger = Logger.getLogger(PregnancyEventController.class);
	 
	 @Autowired
	 CompanyService companyService;
	 
	 @Autowired
	 GroupEventService groupEventService;
	 
	  @RequestMapping(value="/groupEvent", method=RequestMethod.POST)
	   public String loadGroupEvent(HttpServletRequest request, Model model)  
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
			   }
			   else
			   {
				   Company company = companyService.findByCompanyByAutoGeneratedId(activeUser.getCompanyId());
				   model.addAttribute("CompanyId", activeUser.getCompanyId());
				   model.addAttribute("CompanyName", company.getName());
			   }
			   if(request.getParameter("searchedGroupid") != null) 
			   {
				   model.addAttribute("searchedGroupid", request.getParameter("searchedGroupid"));
			   }
			   if(request.getParameter("searchPremiseId") != null) 
			   {
				   model.addAttribute("searchPremiseId", request.getParameter("searchPremiseId"));
			   }
		   }catch(Exception ex)
		   {
			   
		   }
		   model.addAttribute("contentUrl","GroupEvent.jsp");
		   return "template";
	   }
	  
	  @RequestMapping(value = "/addGroupEventDetail", method=RequestMethod.POST)
		public String addPremises(HttpServletRequest request,Model model)
		{
			model.addAttribute("contentUrl","addGroupEventDetail.jsp"); 
			model.addAttribute("groupId",request.getParameter("searchedGroupid"));
			model.addAttribute("companyId",request.getParameter("companyId"));
			model.addAttribute("groupEventId",request.getParameter("groupEventId"));
			model.addAttribute("searchPremiseId", request.getParameter("searchPremiseId"));
			
			
			 
			 if(request.getParameter("groupGeneratedIdSeq") != null && ! "undefined".equals(request.getParameter("groupGeneratedIdSeq"))) 
			   {
				 model.addAttribute("groupGeneratedId",request.getParameter("groupGeneratedIdSeq"));
				 model.addAttribute("groupStartDateTime",request.getParameter("groupStartDateTimeAdd"));
					
			   }
			 else if(null != request.getParameter("companyId"))
			 {
				 try 
				 {
					GroupEvent groupEvent =  groupEventService.getGroupEventByGroupId(request.getParameter("searchedGroupid"),Integer.parseInt(request.getParameter("companyId")), Integer.parseInt(request.getParameter("searchPremiseId")));
					if(null != groupEvent)
					{
						model.addAttribute("groupGeneratedId",groupEvent.getId());
						model.addAttribute("groupStartDateTime",groupEvent.getGroupStartDateTime());
					}
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
					//dto.setStatusMessage("ERROR : "+e.getMessage());
				}
				 catch (PigTraxException e1) {
						e1.printStackTrace();
						//dto.setStatusMessage("ERROR : "+e.getMessage());
					}					
			 }
			return "template";
		}
	  
	  @RequestMapping(value="/pigGroupEvent")
	   public String loadGroupEventGet(HttpServletRequest request, Model model)  
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
			   model.addAttribute("contentUrl","GroupEvent.jsp");
		   }
		   return "template";
	   }
	  
	  @RequestMapping(value="/moveToAnotherGroup")
	   public String moveToAnotherGroupGet(HttpServletRequest request, Model model)  
	   {
		PigTraxUser activeUser = (PigTraxUser) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		/*
		 * logger.info("Compayny ID : "+activeUser.getCompanyId()+"/ Role : "+
		 * activeUser.getUserRole()); Locale ln = request.getLocale();
		 * logger.info("Locale selected : "+ln.getLanguage());
		 * if(request.isUserInRole(RoleType.PigTraxSuperAdmin.getRoleValue()) ||
		 * request.isUserInRole(RoleType.PigTraxDataConfigMgr.getRoleValue())) {
		 * model.addAttribute("contentUrl","company.jsp"); } else {
		 */
		try
		{
			Company company = companyService
					.findByCompanyByAutoGeneratedId(activeUser.getCompanyId());
			model.addAttribute("CompanyId", activeUser.getCompanyId());
			model.addAttribute("CompanyName", company.getName());

		} 
		catch (Exception ex) {

		}
		model.addAttribute("contentUrl", "moveToAnotherGroup.jsp");
		// }
		return "moveToAnotherGroup";
	   }

}
