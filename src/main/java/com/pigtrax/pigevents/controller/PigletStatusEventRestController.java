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
import com.pigtrax.pigevents.dto.PigletStatusEventDto;
import com.pigtrax.pigevents.service.interfaces.PigletStatusEventService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/pigletStatusEvent")
public class PigletStatusEventRestController {
	
	private static final Logger logger = Logger.getLogger(PigletStatusEventRestController.class);
	
	
	@Autowired
	PigletStatusEventService pigletStatusEventService; 
	
	
	/**
	 * Service to save/update the PigletStatus event information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/savePigletStatusEventInformation", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public ServiceResponseDto savePigletStatusEventInformation(HttpServletRequest request, @RequestBody PigletStatusEventDto pigletStatusEventDto)
	{
		logger.info("Inside savePigletStatusEventInformation method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			pigletStatusEventDto.setUserUpdated(activeUser.getUsername());
			pigletStatusEventService.savePigletStatusEvent(pigletStatusEventDto);
			dto.setStatusMessage("Success");
		} catch (PigTraxException e) {
			if(e.isDuplicateStatus())
			{   
				dto.setDuplicateRecord(true);
			}
			dto.setStatusMessage("ERROR : "+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}		
		return dto; 
	}
	
	
	/**
	 * Service to get Pigletstatus information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getPigletStatusEventInformation", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public ServiceResponseDto getPigletStatusEventInformation(HttpServletRequest request, @RequestBody PigletStatusEventDto pigletStatusEventDto)
	{
		logger.info("Inside getPigletStatusEventInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			String language = localeResolver.resolveLocale(request).getLanguage();
			pigletStatusEventDto.setLanguage(language);
			
			List<PigletStatusEventDto> pigletStatusEventsList = pigletStatusEventService.getPigletStatusEvents(pigletStatusEventDto);
			if(pigletStatusEventsList != null && pigletStatusEventsList.size() > 0)
			{
				dto.setPayload(pigletStatusEventsList); 
				dto.setStatusMessage("Success");
			} 
			else
			{
				dto.setStatusMessage("ERROR : Pregnancy Event information not available ");
			}
		} catch (PigTraxException e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR : "+e.getMessage());
		} 
		return dto;
	}
	
	/**
	 * Service to delete the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/deletePigletStatusEventInformation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto deletePigletStatusEventInformation(HttpServletRequest request, @RequestBody PigletStatusEventDto pigletStatusEventDto)
	{
		logger.info("Inside deletePigletStatusEventInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			pigletStatusEventService.deletePigletStatusEvent(pigletStatusEventDto);
			dto.setPayload(pigletStatusEventDto);
			dto.setStatusMessage("Success");
		}
		catch (PigTraxException e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR : "+e.getMessage());
		} 
		catch (Exception e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}
		return dto;
	}
	
	/**
	 * Service to validate the Piglet status event
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/validatePigletStatusEvent", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto validatePigletStatusEvent(HttpServletRequest request, @RequestBody PigletStatusEventDto pigletStatusEventDto)
	{
		logger.info("Inside validatePigletStatusEvent method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			int statusCode = pigletStatusEventService.validatePigletStatusEvent(pigletStatusEventDto);
			dto.setPayload(statusCode);
			dto.setStatusMessage("Success");
		}  catch (Exception e) {
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}
		return dto;
	}	
    
}
