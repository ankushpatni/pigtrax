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
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDetailsDao;
import com.pigtrax.pigevents.dto.GroupEventDto;
import com.pigtrax.pigevents.service.interfaces.GroupEventDetailsService;
import com.pigtrax.pigevents.service.interfaces.GroupEventService;
import com.pigtrax.usermanagement.beans.Company;
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
	 * Service to save the group event information
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
			int rowsInserted = 0;
			if(null != groupEvent && (groupEvent.getId() == null || groupEvent.getId() == 0) )
			{
				rowsInserted = groupEventService.addGroupEvent(groupEvent);
				dto.setRecordAdded(true);
			}
			else if( null != groupEvent && groupEvent.getId() !=0)
			{
				rowsInserted = groupEventService.updateGroupEvent(groupEvent);
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
	 * Service to get the group event information
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getGroupEventInformation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getGroupEventInformation(HttpServletRequest request, @RequestBody GroupEvent groupEvent)
	{
		logger.info("Inside getGroupEventInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			int companyId;
			if( groupEvent.getCompanyId() != null ) 
			   {
				   companyId = groupEvent.getCompanyId();
			   }
			 else
			   {
				   PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					companyId = activeUser.getCompanyId();
			   }
			
			List groupEventAndDetail = groupEventService.getGroupEventAndDetailByGroupId(groupEvent.getGroupId().toUpperCase(), companyId, groupEvent.getPremiseId(), null);
			if(groupEventAndDetail != null && groupEventAndDetail.size()>0 )
			{
				dto.setPayload(groupEventAndDetail);
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
	 * Service to save the group event details
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
			int rowsInserted;
			if(null != groupEventDto && (groupEventDto.getId() == null || groupEventDto.getId() ==0))
			{
				rowsInserted = groupEventDetailsService.addGroupEventDetails(groupEventDto);
			}
			else
			{
				rowsInserted = groupEventDetailsService.updateGroupEventDetails(groupEventDto);
			}
			dto.setStatusMessage("Success");
		}
		catch (PigTraxException e)
		{
			if(e.isDuplicateStatus())
			{
				dto.setDuplicateRecord(true);
			}
			dto.setStatusMessage("ERROR : "+e.getMessage());
		} 
		catch (Exception e) {			
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}		
		return dto; 
	}
	
	/**
	 * Service to get the group event details
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getGroupEventDetail", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getGroupEventDetail(HttpServletRequest request,  @RequestBody Integer groupId)
	{
		logger.info("Inside getGroupEventInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			
			GroupEventDto groupEventDto = groupEventDetailsService.groupEventDetailsListById(groupId);
			if(groupEventDto != null  )
			{
				dto.setPayload(groupEventDto);
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
	 * Service to update the group event details
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/updateGroupEventInformation", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto updateGroupEventInformation(HttpServletRequest request, @RequestBody GroupEvent groupEvent)
	{
		logger.info("Inside addGroupEventDetail method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			groupEvent.setUserUpdated(activeUser.getUsername());
			int rowsInserted;
			if(null != groupEvent && groupEvent.getId() != null && groupEvent.getId() >=0)
			{
				rowsInserted = groupEventService.updateGroupEventStatus(groupEvent);
				dto.setStatusMessage("Success");
			}
			else
			{
				dto.setStatusMessage("ERROR : GroupId is wrong");
			}
			
		}
		catch (PigTraxException e)
		{
			if(e.isDuplicateStatus())
			{
				dto.setDuplicateRecord(true);
			}
			dto.setStatusMessage("ERROR : "+e.getMessage());
		} 
		catch (Exception e) {			
			dto.setStatusMessage("ERROR : "+e.getMessage());
		}		
		return dto; 
	}
	
	
	/**
	 * Service to update the group event from Nursery to Finish
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/promoteToFinish", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto promoteToFinish(HttpServletRequest request, @RequestBody GroupEvent groupEvent)
	{
		logger.info("Inside promoteToFinish method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			groupEvent.setUserUpdated(activeUser.getUsername());
			if( null != groupEvent && groupEvent.getId() !=0)
			{
				int rowsInserted = groupEventService.updateGroupEvent(groupEvent);
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
	 * Service to revert the group event change from Nursery to Finish
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/moveBackToNursery", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto moveBackToNursery(HttpServletRequest request, @RequestBody GroupEvent groupEvent)
	{
		logger.info("Inside moveBackToNursery method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			groupEvent.setUserUpdated(activeUser.getUsername());
			if( null != groupEvent && groupEvent.getId() !=0)
			{
				int rowsInserted = groupEventService.updateGroupEvent(groupEvent);
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
	 * Service to get the group event details for tranfer
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/getGroupEventInformationForTransfer", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto getGroupEventInformationForTransfer(HttpServletRequest request, @RequestBody GroupEvent groupEvent)
	{
		logger.info("Inside getGroupEventInformation method" );
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			int companyId;
			if( groupEvent.getCompanyId() != null ) 
			   {
				   companyId = groupEvent.getCompanyId();
			   }
			 else
			   {
				   PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					companyId = activeUser.getCompanyId();
			   }
			
			List groupEventAndDetail = groupEventService.getGroupEventAndDetailByGroupId(groupEvent.getGroupId().toUpperCase(), companyId, groupEvent.getPremiseId(), groupEvent.getPhaseOfProductionTypeId());
			if(groupEventAndDetail != null && groupEventAndDetail.size()>0 )
			{
				dto.setPayload(groupEventAndDetail);
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
	 * Service to Transfer piglets from Nursery to Nursery or Finish to finish
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/transferToGroup", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto transferToGroup(HttpServletRequest request, @RequestBody GroupEvent groupEvent)
	{
		logger.info("Inside transferToGroup method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			groupEvent.setUserUpdated(activeUser.getUsername());
			if( null != groupEvent && groupEvent.getId() !=0)
			{	
				groupEvent.setFromMove(true);
				int rowsInserted = groupEventService.updateGroupEvent(groupEvent);
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
	 * Service to perform wean To Finish 2
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/weanToFinishPhase2", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto weanToFinishPhase2(HttpServletRequest request, @RequestBody GroupEvent groupEvent)
	{
		logger.info("Inside weanToFinishPhase2 method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			groupEvent.setUserUpdated(activeUser.getUsername());
			if( null != groupEvent && groupEvent.getId() !=0)
			{
				int rowsInserted = groupEventService.updateGroupEvent(groupEvent);
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
	 * Service to undo wean To Finish 2
	 * @return ServiceResponseDto
	 */
	@RequestMapping(value = "/undoWeanToFinishPhase2", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public ServiceResponseDto undoPhase2Movement(HttpServletRequest request, @RequestBody GroupEvent groupEvent)
	{
		logger.info("Inside undoWeanToFinishPhase2 method" ); 
		PigTraxUser activeUser = (PigTraxUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceResponseDto dto = new ServiceResponseDto();
		try {
			groupEvent.setUserUpdated(activeUser.getUsername());
			if( null != groupEvent && groupEvent.getId() !=0)
			{
				int rowsInserted = groupEventService.undoWeanToFinishPhase2(groupEvent);
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
