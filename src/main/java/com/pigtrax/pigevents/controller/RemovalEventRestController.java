package com.pigtrax.pigevents.controller;

import java.util.LinkedList;
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
import com.pigtrax.master.dto.Barn;
import com.pigtrax.master.dto.Premises;
import com.pigtrax.master.service.interfaces.BarnService;
import com.pigtrax.master.service.interfaces.PremisesService;
import com.pigtrax.pigevents.beans.RemovalEvent;
import com.pigtrax.pigevents.beans.RemovalEventExceptSalesDetails;
import com.pigtrax.pigevents.beans.SalesEventDetails;
import com.pigtrax.pigevents.dto.PigInfoDto;
import com.pigtrax.pigevents.service.interfaces.GroupEventDetailsService;
import com.pigtrax.pigevents.service.interfaces.PigInfoService;
import com.pigtrax.pigevents.service.interfaces.RemovalEventExceptSalesService;
import com.pigtrax.pigevents.service.interfaces.RemovalEventService;
import com.pigtrax.pigevents.service.interfaces.SalesEventDetailsService;
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
	RemovalEventExceptSalesService  removalEventExceptSalesService;
	
	@Autowired
	SalesEventDetailsService salesEventDetailsService;
	
	@Autowired
	PigInfoService pigInfoService;
	
	@Autowired
	BarnService barnService;
	
	@Autowired
	GroupEventDetailsService groupEventService;
	
	@Autowired
	PremisesService premiseService;
	
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
	 * Service to save the add Removal Except Sales information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/updateRemovalExceptSales", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto updateRemovalExceptSales(HttpServletRequest request, @RequestBody RemovalEventExceptSalesDetails removalEventExceptSalesDetails)
	{
		logger.info("Inside addGroupEvent method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			removalEventExceptSalesDetails.setUserUpdated(activeUser.getUsername());
			int rowsInserted = 0;
			/*if(null != removalEventExceptSalesDetails && (removalEventExceptSalesDetails.getId() == null || removalEventExceptSalesDetails.getId() == 0) )
			{
				rowsInserted = removalEventExceptSalesService.updateRemovalEventExceptSalesDetails(removalEventExceptSalesDetails);
				dto.setRecordAdded(true);
			}
			else if( null != removalEventExceptSalesDetails && removalEventExceptSalesDetails.getId() !=0)*/
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
			
			List removalEventList = new LinkedList();
			if(null!= removalEvent && removalEvent.getGroupId()!=null)
			{
				removalEventList.add(removalEventExceptSalesService.getRemovalEventExceptSalesDetailsByGroupId(removalEvent.getGroupId(), removalEvent.getCompanyId(), removalEvent.getPremisesId()));
				removalEventList.add(salesEventDetailsService.getSalesEventDetailsListByGroupId(removalEvent.getGroupId(), removalEvent.getCompanyId(), removalEvent.getPremisesId()));
				removalEventList.add(removalEventExceptSalesService.getTransferDetailsByGroupId(removalEvent.getGroupId(), removalEvent.getCompanyId(), removalEvent.getPremisesId()));
				
			}
			
			else if(null!= removalEvent && removalEvent.getPigId()!=null)
			{
				removalEventList.add(removalEventExceptSalesService.getRemovalEventExceptSalesDetailsByPigInfoId(removalEvent.getPigId(), removalEvent.getCompanyId(), removalEvent.getPremisesId()));
				removalEventList.add(salesEventDetailsService.getSalesEventDetailsListByPigId(removalEvent.getPigId(), removalEvent.getCompanyId(), removalEvent.getPremisesId()));
				removalEventList.add(null);  // No seperate transfer list to be passed
			}
			
			if(removalEventList != null && removalEventList.size()>0 )
			{
				dto.setPayload(removalEventList);
				dto.setStatusMessage("Success");
			} 
			else
			{
				dto.setRecordNotPresent(true);
				dto.setStatusMessage("ERROR : Removal Event information not available ");
			}
		} catch (PigTraxException e) {
			e.printStackTrace();
			dto.setStatusMessage("ERROR : "+e.getMessage());
		} 
		return dto;
	}
	
	/**
	 * Service to save the add Sales Event information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/addSalesEventDetails", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto addSalesEventDetails(HttpServletRequest request, @RequestBody SalesEventDetails salesEventDetails)
	{
		logger.info("Inside addGroupEvent method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			salesEventDetails.setUserUpdated(activeUser.getUsername());
			int rowsInserted = 0;
			if(null != salesEventDetails && (salesEventDetails.getId() == null || salesEventDetails.getId() == 0) )
			{
				rowsInserted = salesEventDetailsService.addSalesEventDetails(salesEventDetails);
				dto.setRecordAdded(true);
			}
			else if( null != salesEventDetails && salesEventDetails.getId() !=0)
			{
				rowsInserted = salesEventDetailsService.updateFeedEventDeupdateSalesEventDetailstail(salesEventDetails);
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
	
	@RequestMapping(value = "/updateSalesEventDetails", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto updateSalesEventDetails(HttpServletRequest request, @RequestBody SalesEventDetails salesEventDetails)
	{
		logger.info("Inside addGroupEvent method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			salesEventDetails.setUserUpdated(activeUser.getUsername());
			int rowsInserted = 0;
			/*if(null != salesEventDetails && (salesEventDetails.getId() == null || salesEventDetails.getId() == 0) )
			{
				rowsInserted = salesEventDetailsService.addSalesEventDetails(salesEventDetails);
				dto.setRecordAdded(true);
			}
			else*/ 
			if( null != salesEventDetails && salesEventDetails.getId() !=0)
			{
				rowsInserted = salesEventDetailsService.updateFeedEventDeupdateSalesEventDetailstail(salesEventDetails);
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
	 * Service to get the Sales Event information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getSalesEventDetails", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getSalesEventDetails(HttpServletRequest request, @RequestBody SalesEventDetails salesEventDetails)
	{
		logger.info("Inside getGroupEventInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			
			SalesEventDetails salesEventDetailsRespose = salesEventDetailsService.getSalesEventDetailsById(salesEventDetails.getId());
			if(salesEventDetailsRespose != null )
			{
				dto.setPayload(salesEventDetailsRespose);
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
	 * Service to save the Delete Sales information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/deleteSalesEventDetails", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto deleteSalesEventDetails(HttpServletRequest request, @RequestBody SalesEventDetails salesEventDetails)
	{
		logger.info("Inside addGroupEvent method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			salesEventDetails.setUserUpdated(activeUser.getUsername());
			int rowsInserted = 0;
			if(null != salesEventDetails && salesEventDetails.getId() != 0 )
			{
				rowsInserted = salesEventDetailsService.deleteSalesEventDetails(salesEventDetails);
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
	 * Service to save the add Removal Except Sales information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getRemovalPremise", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getRemovalPremise(HttpServletRequest request, @RequestBody RemovalEventExceptSalesDetails removalEventExceptSalesDetails)
	{
		logger.info("Inside getRemovalPremise method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			Integer premiseId = null;
			if(removalEventExceptSalesDetails.getPigInfoId() != null && removalEventExceptSalesDetails.getPigInfoId() > 0)
			{
				PigInfoDto info = pigInfoService.getPigInformationById(removalEventExceptSalesDetails.getPigInfoId());
				if(info != null && info.getBarnId() != null)
					{
						Barn barn =  barnService.findByBarnByAutoGeneratedId(info.getBarnId());
						if(barn != null)
							premiseId = barn.getPremiseId();
					}
			}
			else if(removalEventExceptSalesDetails.getGroupEventId() != null && removalEventExceptSalesDetails.getGroupEventId() > 0)
			{
				premiseId = null;
			}
			
			Premises premises = premiseService.findByPremisesByAutoGeneratedId(premiseId);
			if(premises != null)
			{
				dto.setPayload(premises);
				dto.setStatusMessage("Success");
			}
			else
			{
				dto.setStatusMessage("ERROR");
			}
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
