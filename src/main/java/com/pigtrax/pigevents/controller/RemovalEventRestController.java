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
import com.pigtrax.pigevents.beans.RemovalEvent;
import com.pigtrax.pigevents.service.interfaces.FeedEventDetailService;
import com.pigtrax.pigevents.service.interfaces.RemovalEventService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/removalEvent")
public class RemovalEventRestController 
{
	private static final Logger logger = Logger.getLogger(PregnancyEventRestController.class);
	@Autowired
	RemovalEventService removalEventService;
	
	@Autowired
	FeedEventDetailService feedEventDetailService;
	
	/**
	 * Service to save the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/addRemovalEvent", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto addFeedEvent(HttpServletRequest request, @RequestBody RemovalEvent removalEvent)
	{
		logger.info("Inside addGroupEvent method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			removalEvent.setUserUpdated(activeUser.getUsername());
			int rowsInserted = 0;
			if(null != removalEvent && (removalEvent.getId() == null || removalEvent.getId() == 0) )
			{
				rowsInserted = removalEventService.addRemovalEvent(removalEvent);
				dto.setRecordAdded(true);
			}
			else if( null != removalEvent && removalEvent.getId() !=0)
			{
				rowsInserted = removalEventService.updateRemovalEvent(removalEvent);
				dto.setRecordUpdated(true);
			}
			dto.setStatusMessage("Success");
		} catch (PigTraxException e) {
			if(e.isDuplicateStatus())
			{
				dto.setDuplicateRecord(true);
			}
			dto.setStatusMessage("ERROR : "+e.getMessage());
		} 
		catch (Exception e)
		{			
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}		
		return dto; 
	}
	

}
