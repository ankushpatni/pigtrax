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
import com.pigtrax.master.dto.Pen;
import com.pigtrax.master.service.interfaces.PenService;
import com.pigtrax.pigevents.dto.FarrowEventDto;
import com.pigtrax.pigevents.service.interfaces.FarrowEventService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/farrowEvent")
public class FarrowEventRestController {
	
	private static final Logger logger = Logger.getLogger(FarrowEventRestController.class);
	
	@Autowired
	PenService penService;
	
	@Autowired
	FarrowEventService farrowEventService;
	
	/**
	 * Service to save the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getPenListForCompany", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getPenListForCompany(HttpServletRequest request, @RequestBody Integer companyId)
	{
		logger.info("Inside getPenListForCompany method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			List<Pen> penList = penService.getPenListByCompanyId(companyId);
			dto.setPayload(penList);
		} catch (PigTraxException e) { 
			dto.setStatusMessage("ERROR : "+e.getMessage());
		} 
		return dto;
	}
	
	
	/**
	 * Service to save the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getPenListForPremise", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getPenListForPremise(HttpServletRequest request, @RequestBody Integer premiseId)
	{
		logger.info("Inside getPenListForPremise method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			List<Pen> penList = penService.getPenListByPremiseId(premiseId); 
			dto.setPayload(penList);
		} catch (PigTraxException e) { 
			dto.setStatusMessage("ERROR : "+e.getMessage());
		} 
		return dto;
	}
		
	/**
	 * Service to save the Farrow event information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/saveFarrowEventInformation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto savePregnancyEventInformation(HttpServletRequest request, @RequestBody FarrowEventDto farrowEventDto)
	{
		logger.info("Inside savePregnancyEventInformation method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			farrowEventDto.setUserUpdated(activeUser.getUsername());
			int rowsInserted = farrowEventService.saveFarrowEventInformation(farrowEventDto);
			dto.setStatusMessage("Success");
		} catch (PigTraxException e) {
			//e.printStackTrace();
			if(e.isDuplicateStatus())
			{
				dto.setDuplicateRecord(true);
				dto.setStatusMessage("ERROR : "+e.getMessage());
			}
			else if(e.getMessage().equalsIgnoreCase("INVALID-PREGNANCY-RECORD"))
				dto.setStatusMessage("ERR:INVALID-PREGNANCY-RECORD");
			else
				dto.setStatusMessage("ERROR : "+e.getMessage());
			
		} catch (Exception e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}		
		return dto; 
	}
	
	
	/**
	 * Service to get pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getFarrowEventInformation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getFarrowEventInformation(HttpServletRequest request, @RequestBody FarrowEventDto farrowEventDto)
	{
		logger.info("Inside getFarrowEventInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			String language = localeResolver.resolveLocale(request).getLanguage();
			farrowEventDto.setLanguage(language);
			
			List<FarrowEventDto> farrowEventDtoList = farrowEventService.getFarrowEvents(farrowEventDto);
			if(farrowEventDtoList != null && farrowEventDtoList.size() > 0)
			{
				dto.setPayload(farrowEventDtoList);
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
	@RequestMapping(value = "/deleteFarrowEvent", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto deleteFarrowEvent(HttpServletRequest request, @RequestBody Integer farrowEventId)
	{
		logger.info("Inside deleteFarrowEvent method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			farrowEventService.deleteFarrowEvent(farrowEventId);
			dto.setPayload(farrowEventId);
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
    	
	
	@RequestMapping(value = "/validateFarrowEvent", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public ServiceResponseDto validatePregnancyEvent(HttpServletRequest request, @RequestBody FarrowEventDto farrowEventDto)
	{
		ServiceResponseDto dto = new ServiceResponseDto();
		int statusCode = farrowEventService.validateFarrowEvent(farrowEventDto);
		logger.info("status Code = "+statusCode);
		dto.setPayload(statusCode);
		return dto;
	}
	
	@RequestMapping(value = "/getFarrowEventDetails", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public ServiceResponseDto getFarrowEventDetails(HttpServletRequest request, @RequestBody FarrowEventDto farrowEventDto)
	{
		ServiceResponseDto dto = new ServiceResponseDto();
		FarrowEventDto eventDto;
		try {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			String language = localeResolver.resolveLocale(request).getLanguage();
			farrowEventDto.setLanguage(language);
			
			eventDto = farrowEventService.getFarrowEventDetails(farrowEventDto);
			if(eventDto != null && eventDto.getId() > 0)
				dto.setPayload(eventDto);
			else
				dto.setStatusMessage("ERROR: Not found");
		} catch (PigTraxException e) {
			dto.setStatusMessage("ERROR: Not found");
		}
		return dto;
	}
	
	@RequestMapping(value = "/getFarrowEventDetailsById", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public ServiceResponseDto getFarrowEventDetails(HttpServletRequest request, @RequestBody Integer farrowEventId)
	{
		ServiceResponseDto dto = new ServiceResponseDto();
		FarrowEventDto eventDto;
		try {
			eventDto = farrowEventService.getFarrowEventDetails(farrowEventId);
			if(eventDto != null && eventDto.getId() > 0)
				dto.setPayload(eventDto);
			else
				dto.setStatusMessage("ERROR: Not found");
		} catch (PigTraxException e) {
			dto.setStatusMessage("ERROR: Not found");
		}
		return dto;
	}
	
    
}
