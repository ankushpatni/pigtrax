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

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.dto.BreedingEventDto;
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
			int rowsInserted = breedingEventService.saveBreedingEventInformation(breedingEventDto);
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
	@RequestMapping(value = "/getBreedingEventInformation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getBreedingEventInformation( @RequestBody BreedingEventDto breedingEventDto)
	{
		logger.info("Inside getBreedingEventInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			breedingEventDto = breedingEventService.getBreedingEventInformation(breedingEventDto.getServiceId(), breedingEventDto.getCompanyId());
			if(breedingEventDto != null && breedingEventDto.getId() != null)
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
	@RequestMapping(value = "/validateBreedingEvent", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto validateBreedingEvent(HttpServletRequest request, @RequestBody BreedingEventDto breedingEventDto)
	{
		logger.info("Inside validateBreedingEvent method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			int statusCode = breedingEventService.validateBreedingEvent(breedingEventDto);
			dto.setPayload(statusCode);
			dto.setStatusMessage("Success");
		}  catch (Exception e) {
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}
		return dto;
	}
    
	
    
}
