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
import com.pigtrax.pigevents.dto.MatingDetailsDto;
import com.pigtrax.pigevents.service.interfaces.MatingDetailsService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/matingDetails")
public class MatingDetailsRestController {
	
	private static final Logger logger = Logger.getLogger(MatingDetailsRestController.class);
	
	
	@Autowired
	MatingDetailsService matingDetailsService;
	
	/**
	 * Service to save the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/saveMatingDetails", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto saveMatingDetails(HttpServletRequest request, @RequestBody MatingDetailsDto matingDetailsDto)
	{
		logger.info("Inside saveMatingDetails method" );
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			matingDetailsDto.setUserUpdated(activeUser.getUsername());
			matingDetailsDto =   matingDetailsService.saveMatingDetails(matingDetailsDto);  
			dto.setPayload(matingDetailsDto);
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
	 * Service to save the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/deleteMatingDetails", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto deleteMatingDetails(HttpServletRequest request, @RequestBody MatingDetailsDto matingDetailsDto)
	{
		logger.info("Inside deleteMatingDetails method" );
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			matingDetailsDto.setUserUpdated(activeUser.getUsername());
			matingDetailsDto =   matingDetailsService.deleteMatingDetails(matingDetailsDto);			
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
	
}
