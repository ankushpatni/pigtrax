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

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.dto.PregnancyEventDto;
import com.pigtrax.pigevents.service.interfaces.PregnancyEventService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/pregnancyEvent")
public class PregnancyEventRestController {
	
	private static final Logger logger = Logger.getLogger(PregnancyEventRestController.class);
	
	
	@Autowired
	PregnancyEventService pregnancyEventService;
	
	/**
	 * Service to save the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/savePregnancyEventInformation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto savePregnancyEventInformation(HttpServletRequest request, @RequestBody PregnancyEventDto pregnancyEventDto)
	{
		logger.info("Inside savePregnancyEventInformation method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			pregnancyEventDto.setUserUpdated(activeUser.getUsername());
			int rowsInserted = pregnancyEventService.savePregnancyEventInformation(pregnancyEventDto);
			dto.setStatusMessage("Success");
		} catch (PigTraxException e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR : "+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}		
		return dto; 
	}
	
	
	/**
	 * Service to save the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getPregnancyEventInformation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getPregnancyEventInformation( @RequestBody PregnancyEventDto pregnancyEventDto)
	{
		logger.info("Inside getBreedingEventInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			List<PregnancyEventDto> pregnancyEventDtoList = pregnancyEventService.getPregnancyEvents(pregnancyEventDto.getPigId(), pregnancyEventDto.getCompanyId());
			if(pregnancyEventDtoList != null && pregnancyEventDtoList.size() > 0)
			{
				dto.setPayload(pregnancyEventDtoList);
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
	@RequestMapping(value = "/deletePregnancyEvent", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto deletePregnancyEvent(HttpServletRequest request, @RequestBody Integer pregnancyEventId)
	{
		logger.info("Inside deletePregnancyEvent method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			pregnancyEventService.deletePregnancyEvent(pregnancyEventId);
			dto.setPayload(pregnancyEventId);
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
    
		
    
}
