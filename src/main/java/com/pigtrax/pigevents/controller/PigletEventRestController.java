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
import com.pigtrax.pigevents.dto.PigletEventDto;
import com.pigtrax.pigevents.service.interfaces.FarrowEventService;
import com.pigtrax.pigevents.service.interfaces.PigletEventService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/pigletEvent")
public class PigletEventRestController {
	
	private static final Logger logger = Logger.getLogger(PigletEventRestController.class);
	
	
	@Autowired
	PigletEventService pigletEventService;
	
	@Autowired
	FarrowEventService farrowEventService;
	
		
	/**
	 * Service to save the Piglet event information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/savePigletEventInformation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto savePigletEventInformation(HttpServletRequest request, @RequestBody PigletEventDto pigletEventDto)
	{
		logger.info("Inside savePigletEventInformation method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			pigletEventDto.setUserUpdated(activeUser.getUsername());
			int rowsInserted = pigletEventService.savePigletEventInformation(pigletEventDto);
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
	 * Service to get Piglet information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getPigletEventInformation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getPigletEventInformation(HttpServletRequest request, @RequestBody PigletEventDto pigletEventDto)
	{
		logger.info("Inside getPigletEventInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			String language = localeResolver.resolveLocale(request).getLanguage();
			pigletEventDto.setLanguage(language);
			
			List<PigletEventDto> pigletEventDtoList = pigletEventService.getPigletEvents(pigletEventDto);
			if(pigletEventDtoList != null && pigletEventDtoList.size() > 0)
			{
				dto.setPayload(pigletEventDtoList);
				dto.setStatusMessage("Success");
			} 
			else
			{
				dto.setStatusMessage("ERROR : Piglet Event information not available ");
			}
		} catch (PigTraxException e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR : "+e.getMessage());
		} 
		return dto;
	}
	
	/**
	 * Service to delete the Piglet information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/deletePigletEvent", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto deletePigletEvent(HttpServletRequest request, @RequestBody Integer pigletEventId)
	{
		logger.info("Inside deletePigletEvent method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			pigletEventService.deletePigletEvent(pigletEventId); 
			dto.setPayload(pigletEventId);
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
	 * Service to get Piglet information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/checkForLitterId", method=RequestMethod.POST, produces="application/json", consumes="application/json")
	@ResponseBody
	public ServiceResponseDto checkForLitterId(HttpServletRequest request, @RequestBody PigletEventDto pigletEventDto)
	{
		logger.info("Inside checkForLitterId method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			String language = localeResolver.resolveLocale(request).getLanguage();
			pigletEventDto.setLanguage(language);
			
			if(pigletEventDto != null)
			{
				Integer farrowEventId = farrowEventService.getFarrowEventIdByLitterId(pigletEventDto.getPigId(), pigletEventDto.getCompanyId(), pigletEventDto.getPremiseId(), pigletEventDto.getLitterId());
			
				if(farrowEventId != null && farrowEventId !=0)
					dto.setStatusMessage("Success");
				else
					dto.setStatusMessage("ERROR");
			}
		} catch (PigTraxException e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR : "+e.getMessage());
		} 
		return dto;
	}
	
//    	
//	
//	@RequestMapping(value = "/validatePigletEvent", method=RequestMethod.POST, produces="application/json", consumes="application/json")
//	@ResponseBody
//	public ServiceResponseDto validatePigletEvent(HttpServletRequest request, @RequestBody PigletEventDto pigletEventDto)
//	{
//		ServiceResponseDto dto = new ServiceResponseDto();
//		int statusCode = pigletEventService.validateFarrowEvent(pigletEventDto);
//		dto.setPayload(statusCode);
//		return dto;
//	}
    
}
