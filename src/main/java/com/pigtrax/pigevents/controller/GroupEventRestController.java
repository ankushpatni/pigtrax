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
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.service.interfaces.GroupEventService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/groupEvent")
public class GroupEventRestController {
	
private static final Logger logger = Logger.getLogger(PregnancyEventRestController.class);
	
	
	@Autowired
	GroupEventService groupEventService;
	
	/**
	 * Service to save the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/addGroupEvent", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto addGroupEvent(HttpServletRequest request, @RequestBody GroupEvent groupEvent)
	{
		logger.info("Inside savePregnancyEventInformation method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			groupEvent.setUserUpdated(activeUser.getUsername());
			int rowsInserted = groupEventService.addGroupEvent(groupEvent);
			dto.setStatusMessage("Success");
		} catch (PigTraxException e) {
			if(e.isDuplicateStatus())
			{
				dto.setDuplicateRecord(true);
			}
			dto.setStatusMessage("ERROR : "+e.getMessage());
		} catch (Exception e) {			
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}		
		return dto; 
	}
	
	
	

}
