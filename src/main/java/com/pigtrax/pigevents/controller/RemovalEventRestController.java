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
import com.pigtrax.pigevents.beans.RemovalEvent;
import com.pigtrax.pigevents.beans.RemovalEventExceptSalesDetails;
import com.pigtrax.pigevents.service.interfaces.FeedEventDetailService;
import com.pigtrax.pigevents.service.interfaces.RemovalEventExceptSalesService;
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
	
	@Autowired
	RemovalEventExceptSalesService  removalEventExceptSalesService;
	
	/**
	 * Service to save the Removal Event information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/addRemovalEvent", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto addRemovalEvent(HttpServletRequest request, @RequestBody RemovalEvent removalEvent)
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
	
	
	/**
	 * Service to get the Removal Event information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getRemovalEventInformation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getRemovalEventInformation(HttpServletRequest request, @RequestBody RemovalEvent removalEvent)
	{
		logger.info("Inside getGroupEventInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			
			List removalEventAndDetail = removalEventService.getRemovalEventAndDetailByRemovalId(removalEvent.getRemovalId());
			if(removalEventAndDetail != null && removalEventAndDetail.size()>0 )
			{
				dto.setPayload(removalEventAndDetail);
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
	 * Service to save the add Removal Except Sales information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/addRemovalExceptSales", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto addRemovalExceptSales(HttpServletRequest request, @RequestBody RemovalEventExceptSalesDetails removalEventExceptSalesDetails)
	{
		logger.info("Inside addGroupEvent method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			removalEventExceptSalesDetails.setUserUpdated(activeUser.getUsername());
			int rowsInserted = 0;
			if(null != removalEventExceptSalesDetails && (removalEventExceptSalesDetails.getId() == null || removalEventExceptSalesDetails.getId() == 0) )
			{
				rowsInserted = removalEventExceptSalesService.addRemovalEventExceptSalesDetails(removalEventExceptSalesDetails);
				dto.setRecordAdded(true);
			}
			else if( null != removalEventExceptSalesDetails && removalEventExceptSalesDetails.getId() !=0)
			{
				rowsInserted = removalEventExceptSalesService.updateFeedEventDeupdateRemovalEventExceptSalesDetailstail(removalEventExceptSalesDetails);
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
	
	
	/**
	 * Service to get the Removal Except Sales Event information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getRemovalExceptSales", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getRemovalExceptSales(HttpServletRequest request, @RequestBody RemovalEventExceptSalesDetails removalEventExceptSalesDetails)
	{
		logger.info("Inside getGroupEventInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			
			RemovalEventExceptSalesDetails removalEventExceptSalesDetailsRespose = removalEventExceptSalesService.getRemovalEventExceptSalesDetailsById(removalEventExceptSalesDetails.getId());
			if(removalEventExceptSalesDetailsRespose != null )
			{
				dto.setPayload(removalEventExceptSalesDetailsRespose);
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
	 * Service to save the Delete Removal Except Sales information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/deleteRemovalExceptSales", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto deleteRemovalExceptSales(HttpServletRequest request, @RequestBody RemovalEventExceptSalesDetails removalEventExceptSalesDetails)
	{
		logger.info("Inside addGroupEvent method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			removalEventExceptSalesDetails.setUserUpdated(activeUser.getUsername());
			int rowsInserted = 0;
			if(null != removalEventExceptSalesDetails && removalEventExceptSalesDetails.getId() != 0 )
			{
				rowsInserted = removalEventExceptSalesService.deleteRemovalExceptSales(removalEventExceptSalesDetails);
				dto.setRecordAdded(true);
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
	
	/**
	 * Service to get the Removal Event information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getRemovalEventInformationList", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getRemovalEventInformationList(HttpServletRequest request, @RequestBody RemovalEvent removalEvent)
	{
		logger.info("Inside getGroupEventInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			
			List removalEventList = removalEventService.getRemovalEventListGroupOrPigInfo(removalEvent);
			if(removalEventList != null && removalEventList.size()>0 )
			{
				dto.setPayload(removalEventList);
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

}
