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
import com.pigtrax.pigevents.dto.BreedingEventDto;
import com.pigtrax.pigevents.dto.MatingDetailsDto;
import com.pigtrax.pigevents.service.interfaces.BreedingEventService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/breedingEvent")
public class BreedingEventRestController {
	
	private static final Logger logger = Logger.getLogger(BreedingEventRestController.class);
	 
	
	@Autowired
	BreedingEventService breedingEventService;
	
	/**
	 * Service to save the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/saveBreedingEventInformation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto saveBreedingEventInformation(HttpServletRequest request, @RequestBody BreedingEventDto breedingEventDto)
	{
		logger.info("Inside saveBreedingEventInformation method" );
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			breedingEventDto.setUserUpdated(activeUser.getUsername());
			breedingEventDto =   breedingEventService.saveBreedingEventInformation(breedingEventDto); 
			if(breedingEventDto != null)
			{
				dto.setPayload(breedingEventDto);
				dto.setStatusMessage("Success");
			}
		} catch (PigTraxException e) {
			
			if("INCOMPLETE_SERVICE_CYCLE".equalsIgnoreCase(e.getMessage()))
			{
				dto.setStatusMessage("INCOMPLETE_SERVICE_CYCLE");
			}
			
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
	 * Service to save the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getBreedingEventInformation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getBreedingEventInformation(HttpServletRequest request, @RequestBody BreedingEventDto breedingEventDto)
	{
		logger.info("Inside getBreedingEventInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
				
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			String language = localeResolver.resolveLocale(request).getLanguage();
			breedingEventDto.setLanguage(language);
			
			List<BreedingEventDto> breedingEventDtoList = breedingEventService.getBreedingEventInformationList(breedingEventDto);
			logger.info("size : "+(breedingEventDtoList != null ? breedingEventDtoList.size() : 0));
			if(breedingEventDtoList != null && breedingEventDtoList.size() > 0)
			{
				dto.setPayload(breedingEventDtoList);
				dto.setStatusMessage("Success");
			}
			else
			{
				dto.setStatusMessage("ERROR : Breeding Event information not available ");
			}
		} catch (PigTraxException e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}
		return dto;
	}
	
	
	
	/**
	 * Service to save the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getBreedingEventDetails", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getBreedingEventDetails(HttpServletRequest request, @RequestBody Integer breedingEventId)
	{
		logger.info("Inside getBreedingEventDetails method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
				
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			String language = localeResolver.resolveLocale(request).getLanguage();
			
			
			BreedingEventDto breedingEventDto = breedingEventService.getBreedingEventInformation(breedingEventId);			
			if(breedingEventDto != null && breedingEventDto.getId() > 0)
			{
				dto.setPayload(breedingEventDto);
				dto.setStatusMessage("Success");
			}
			else
			{
				dto.setStatusMessage("ERROR : Breeding Event information not available ");
			}
		} catch (PigTraxException e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}
		return dto;
	}
	
	
	/**
	 * Service to save the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getActiveBreedingServices", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getActiveBreedingServices(HttpServletRequest request, @RequestBody BreedingEventDto breedingEventDto)
	{
		logger.info("Inside getBreedingEventInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
				
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			String language = localeResolver.resolveLocale(request).getLanguage();
			breedingEventDto.setLanguage(language); 
			
			List<BreedingEventDto> breedingEventDtoList = breedingEventService.getActiveBreedingServices(breedingEventDto);
			logger.info("size : "+(breedingEventDtoList != null ? breedingEventDtoList.size() : 0));
			if(breedingEventDtoList != null && breedingEventDtoList.size() > 0)
			{
				dto.setPayload(breedingEventDtoList);
				dto.setStatusMessage("Success");
			}
			else
			{
				dto.setStatusMessage("ERROR : Breeding Event information not available ");
			}
		} catch (PigTraxException e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}
		return dto;
	}
	
	
	
	/**
	 * Service to save the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getPregnantBreedingServices", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getPregnantBreedingServices(HttpServletRequest request, @RequestBody BreedingEventDto breedingEventDto)
	{
		logger.info("Inside getPregnantBreedingServices method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
				
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			String language = localeResolver.resolveLocale(request).getLanguage();
			breedingEventDto.setLanguage(language); 
			
			List<BreedingEventDto> breedingEventDtoList = breedingEventService.getPregnantBreedingServices(breedingEventDto);
			logger.info("size : "+(breedingEventDtoList != null ? breedingEventDtoList.size() : 0));
			if(breedingEventDtoList != null && breedingEventDtoList.size() > 0)
			{
				dto.setPayload(breedingEventDtoList);
				dto.setStatusMessage("Success");
			}
			else
			{
				dto.setStatusMessage("ERROR : Breeding Event information not available ");
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
	@RequestMapping(value = "/deleteBreedingEventInfo", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto deleteBreedingEventInfo(HttpServletRequest request, @RequestBody Integer id)
	{
		logger.info("Inside deleteBreedingEventInfo method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			breedingEventService.deleteBreedingEventInfo(id);
			dto.setPayload(id);
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
	 * Service to delete the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/validateMatingDetails", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto validateMatingDetails(HttpServletRequest request, @RequestBody MatingDetailsDto matingDetailsDto)
	{
		logger.info("Inside validateBreedingEvent method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			String statusCode = breedingEventService.validateBreedingEvent(matingDetailsDto);  
			dto.setPayload(statusCode);
			dto.setStatusMessage("Success");
		}  catch (Exception e) {
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}
		return dto;
	}
	
    
}
