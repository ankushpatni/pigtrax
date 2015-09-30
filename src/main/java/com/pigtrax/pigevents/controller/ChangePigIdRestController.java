package com.pigtrax.pigevents.controller;

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
import com.pigtrax.pigevents.dto.PigInfoDto;
import com.pigtrax.pigevents.service.interfaces.ChangePigIdService;
import com.pigtrax.pigevents.service.interfaces.PigInfoService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/changePigId")
public class ChangePigIdRestController {
	
	private static final Logger logger = Logger.getLogger(ChangePigIdRestController.class);
	
	
	@Autowired
	ChangePigIdService changePigIdService;
	
	@Autowired
	PigInfoService pigInfoService;
	
	/**
	 * Service to retrive the list of employees
	 * @return ServiceResponseDto
	 */
	@RequestMapping(method=RequestMethod.POST, produces="application/json",consumes="application/json")
	@ResponseBody
	public ServiceResponseDto changePigId(HttpServletRequest request, @RequestBody PigInfoDto pigInfoDto)
	{
		logger.info("Inside changePigId method" );
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String language = localeResolver.resolveLocale(request).getLanguage();
		
		pigInfoDto.setUserUpdated(activeUser.getUsername());
		
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			pigInfoService.changePigId(pigInfoDto.getId(), pigInfoDto.getNewPigId());
			changePigIdService.storeChangedPigId(pigInfoDto);
		} catch (PigTraxException e) { 
			if(e.isDuplicateStatus())
			{
				dto.setDuplicateRecord(true);
			}
			dto.setStatusMessage("ERROR");
		}
		return dto;
	}
	
}
