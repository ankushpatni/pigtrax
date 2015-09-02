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
import com.pigtrax.pigevents.beans.FeedEvent;
import com.pigtrax.pigevents.beans.FeedEventDetail;
import com.pigtrax.pigevents.service.interfaces.FeedEventDetailService;
import com.pigtrax.pigevents.service.interfaces.FeedEventService;
import com.pigtrax.usermanagement.beans.PigTraxUser;
import com.pigtrax.usermanagement.dto.ServiceResponseDto;


@RestController
@RequestMapping("rest/feedEvent")
public class FeedEventRestController {
	
	private static final Logger logger = Logger.getLogger(PregnancyEventRestController.class);
	@Autowired
	FeedEventService feedEventService;
	
	@Autowired
	FeedEventDetailService feedEventDetailService;
	
	/**
	 * Service to save the pig information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/addFeedEvent", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto addFeedEvent(HttpServletRequest request, @RequestBody FeedEvent feedEvent)
	{
		logger.info("Inside addGroupEvent method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			feedEvent.setUserUpdated(activeUser.getUsername());
			int rowsInserted = 0;
			if(null != feedEvent && (feedEvent.getId() == null || feedEvent.getId() == 0) )
			{
				rowsInserted = feedEventService.addFeedEvent(feedEvent);
				dto.setRecordAdded(true);
			}
			else if( null != feedEvent && feedEvent.getId() !=0)
			{
				rowsInserted = feedEventService.updateFeedEvent(feedEvent);
				dto.setRecordUpdated(true);
			}
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
	@RequestMapping(value = "/getFeedEventInformation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getFeedEventInformation(HttpServletRequest request, @RequestBody FeedEvent feedEvent)
	{
		logger.info("Inside getGroupEventInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			
			List feedEventAndDetail = feedEventService.getFeedEventAndDetailByTicketNumber(feedEvent.getTicketNumber());
			if(feedEventAndDetail != null && feedEventAndDetail.size()>0 )
			{
				dto.setPayload(feedEventAndDetail);
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
	@RequestMapping(value = "/getFeedEventDetailMasterData", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getFeedEventDetailMasterData(HttpServletRequest request, @RequestBody FeedEvent feedEvent)
	{
		logger.info("Inside getGroupEventInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			
			List feedEventAndDetail = feedEventService.getFeedEventAndDetailByTicketNumber(feedEvent.getTicketNumber());
			if(feedEventAndDetail != null && feedEventAndDetail.size()>0 )
			{
				dto.setPayload(feedEventAndDetail);
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
	
	@RequestMapping(value = "/addFeedEventDetail", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto addFeedEventDetail(HttpServletRequest request, @RequestBody FeedEventDetail feedEventDetail)
	{
		logger.info("Inside addGroupEvent method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			feedEventDetail.setUserUpdated(activeUser.getUsername());
			int rowsInserted = 0;
			if(null != feedEventDetail && (feedEventDetail.getId() == null || feedEventDetail.getId() == 0) )
			{
				rowsInserted = feedEventDetailService.addFeedEventDetail(feedEventDetail);
				dto.setRecordAdded(true);
			}
			else if( null != feedEventDetail && feedEventDetail.getId() !=0)
			{
				rowsInserted = feedEventDetailService.updateFeedEventDetail(feedEventDetail);
				dto.setRecordUpdated(true);
			}
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
