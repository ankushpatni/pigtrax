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
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.dto.GroupEventDto;
import com.pigtrax.pigevents.service.interfaces.GroupEventDetailsService;
import com.pigtrax.pigevents.service.interfaces.GroupEventService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;

@RestController
@RequestMapping("rest/groupEvent")
public class GroupEventRestController {
	
private static final Logger logger = Logger.getLogger(PregnancyEventRestController.class);
	
	
	@Autowired
	GroupEventService groupEventService;
	
	@Autowired
	GroupEventDetailsService groupEventDetailsService;
	
	/**
	 * Service to save the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/addGroupEvent", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto addGroupEvent(HttpServletRequest request, @RequestBody GroupEvent groupEvent)
	{
		logger.info("Inside addGroupEvent method" ); 
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
	
	/**
	 * Service to save the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getGroupEventInformation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getGroupEventInformation(HttpServletRequest request,  @RequestBody String groupId)
	{
		logger.info("Inside getGroupEventInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
			String language = localeResolver.resolveLocale(request).getLanguage();
			
			GroupEvent groupEvent = groupEventService.getGroupEventByGroupId(groupId);
			if(groupEvent != null)
			{
				dto.setPayload(groupEvent);
				dto.setStatusMessage("Success");
			} 
			else
			{
				dto.setRecordNotPresent(true);
				dto.setStatusMessage("ERROR : Group Event information not available ");
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
	@RequestMapping(value = "/addGroupEventDetail", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto addGroupEventDetail(HttpServletRequest request, @RequestBody GroupEventDto groupEventDto)
	{
		logger.info("Inside addGroupEventDetail method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			groupEventDto.setUserUpdated(activeUser.getUsername());
			int rowsInserted = groupEventDetailsService.addGroupEventDetails(groupEventDto);
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
