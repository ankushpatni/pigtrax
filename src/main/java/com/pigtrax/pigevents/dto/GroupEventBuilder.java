package com.pigtrax.pigevents.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.beans.PregnancyEvent;

@Component
public class GroupEventBuilder {
	
	/**
	 * Convert Bean to DTO
	 * @param dto
	 * @return
	 */
	public GroupEvent convertToBean(GroupEventDto dto)
	   {
		GroupEvent groupEvent = new GroupEvent();
		   if(dto != null)
		   {
			   groupEvent.setId(dto.getId());
				groupEvent.setGroupId(dto.getGroupId());
				groupEvent.setOrigin(dto.getOrigin());
				groupEvent.setBeginDateTime(dto.getBeginDateTime());
				groupEvent.setRoomId(dto.getRoomId());
				groupEvent.setEmployeeGroupId(dto.getEmployeeGroupId());
				groupEvent.setNumberOfPigs(dto.getNumberOfPigs());
				groupEvent.setWeightInKgs(dto.getWeightInKgs());
				groupEvent.setInventoryAdjustment(dto.getInventoryAdjustment());
				groupEvent.setGroupCloseDateTime(dto.getGroupCloseDateTime());
				groupEvent.setRemarks(dto.getRemarks());
				groupEvent.setLastUpdated(dto.getLastUpdated());
				groupEvent.setUserUpdated(dto.getUserUpdated());
				groupEvent.setPhaseOfProductionTypeId(dto.getPhaseOfProductionTypeId());
		   }
		   return groupEvent;
	   }
	
	 /**
	    * Convert DTO to Bean
	    * @param info
	    * @return
	    */
	   public GroupEventDto convertToDto(GroupEvent event)
	   {
		   GroupEventDto dto = new GroupEventDto();
		   if(event != null)
		   {
			    dto.setId(event.getId());
				dto.setGroupId(event.getGroupId());
				dto.setOrigin(event.getOrigin());
				dto.setBeginDateTime(event.getBeginDateTime());
				dto.setRoomId(event.getRoomId());
				dto.setEmployeeGroupId(event.getEmployeeGroupId());
				dto.setNumberOfPigs(event.getNumberOfPigs());
				dto.setWeightInKgs(event.getWeightInKgs());
				dto.setInventoryAdjustment(event.getInventoryAdjustment());
				dto.setGroupCloseDateTime(event.getGroupCloseDateTime());
				dto.setRemarks(event.getRemarks());
				dto.setLastUpdated(event.getLastUpdated());
				dto.setUserUpdated(event.getUserUpdated());
				dto.setPhaseOfProductionTypeId(dto.getPhaseOfProductionTypeId());
		   }
		   return dto;
	   }
	   
	   /**
	    * Convert list of PreganancyEvent to Dto
	    * @param pregnancyEvents
	    * @return
	    */
	   public List<GroupEventDto> convertToDtos(List<GroupEvent> groupEvents)
	   {
		   List<GroupEventDto> groupEventList = new ArrayList<GroupEventDto>();
		   for(GroupEvent event : groupEvents)
		   {
			   groupEventList.add(convertToDto(event));
		   }
		   return groupEventList;
	   }
	   
	   

}
