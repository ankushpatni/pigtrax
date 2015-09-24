package com.pigtrax.pigevents.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.dto.CompanyTargetDto;
import com.pigtrax.pigevents.service.interfaces.CompanyTargetService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/companyTarget")
public class CompanyTargetRestController {
	
	private static final Logger logger = Logger.getLogger(CompanyTargetRestController.class);
	
	@Autowired
	CompanyTargetService companyTargetService;
	
	/**
	 * Service to retrive the list of company targets
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getCompanyTargets", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public ServiceResponseDto getCompanyTargets(HttpServletRequest request, @RequestBody CompanyTargetDto companyTargetDto)
	{
		logger.info("Inside getCompanyTargets method" );
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		companyTargetDto.setLanguage(language);
		ServiceResponseDto dto = new ServiceResponseDto();
		try{
		
			List<CompanyTargetDto> companyTargets = companyTargetService.getCompanyTargets(companyTargetDto);
			dto.setPayload(companyTargets);
			dto.setStatusMessage("success");
			
		} catch (PigTraxException e) {
			dto.setStatusMessage("ERROR");
		}
		return dto;
	}
	
	/**
	 * Service to save the company target
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/saveCompanyTarget", method=RequestMethod.POST, produces="application/json", consumes="application/json") 
	@ResponseBody
	public ServiceResponseDto saveCompanyTarget(HttpServletRequest request, @RequestBody CompanyTargetDto companyTargetDto)
	{
		logger.info("Inside saveCompanyTarget method" );
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();		
		ServiceResponseDto dto = new ServiceResponseDto();
		try{
			companyTargetDto.setUserUpdated(activeUser.getUsername());
			companyTargetDto.setLanguage(language);
			companyTargetDto = companyTargetService.saveCompanyTarget(companyTargetDto);
			dto.setPayload(companyTargetDto);
			dto.setStatusMessage("success");
			
		} catch (PigTraxException e) {
			dto.setStatusMessage("ERROR");
		}
		return dto;
	}
	
	
	/**
	 * Service to retrive the list of company targets
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/deleteTargetDetails", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public ServiceResponseDto deleteTargetDetails(HttpServletRequest request, @RequestBody CompanyTargetDto companyTargetDto)
	{
		logger.info("Inside deleteTargetDetails method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try{
			
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			String language = localeResolver.resolveLocale(request).getLanguage();
			companyTargetDto.setLanguage(language); 
		 
			List<CompanyTargetDto> companyTargets = companyTargetService.deleteTargetDetails(companyTargetDto);
			dto.setPayload(companyTargets); 
			dto.setStatusMessage("success");
			
		} catch (PigTraxException e) {
			dto.setStatusMessage("ERROR");
		}
		return dto;
	}
	
	
}
