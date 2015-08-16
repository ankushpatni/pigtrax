package com.pigtrax.pigevents.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pigtrax.pigevents.beans.GroupEventDetails;
import com.pigtrax.pigevents.beans.PregnancyEvent;

@Component
public class GroupEventBuilder {
	
	/**
	 * Convert Bean to DTO
	 * @param dto
	 * @return
	 */
	public GroupEventDetails convertToBean(GroupEventDto dto)
	   {
		GroupEventDetails groupEventDetails = new GroupEventDetails();
		   if(dto != null)
		   {
			   groupEventDetails.setId(dto.getId());
				groupEventDetails.setGroupId(dto.getGroupId());
				groupEventDetails.setOrigin(dto.getOrigin());
				groupEventDetails.setDateOfEntry(dto.getDateOfEntry());
				groupEventDetails.setRoomId(dto.getRoomId());
				groupEventDetails.setEmployeeGroupId(dto.getEmployeeGroupId());
				groupEventDetails.setNumberOfPigs(dto.getNumberOfPigs());
				groupEventDetails.setWeightInKgs(dto.getWeightInKgs());
				groupEventDetails.setInventoryAdjustment(dto.getInventoryAdjustment());
				groupEventDetails.setGroupCloseDateTime(dto.getGroupCloseDateTime());
				groupEventDetails.setRemarks(dto.getRemarks());
				groupEventDetails.setLastUpdated(dto.getLastUpdated());
				groupEventDetails.setUserUpdated(dto.getUserUpdated());
				groupEventDetails.setPhaseOfProductionTypeId(dto.getPhaseOfProductionTypeId());
		   }
		   return groupEventDetails;
	   }
	
	 /**
	    * Convert DTO to Bean
	    * @param info
	    * @return
	    */
	   public GroupEventDto convertToDto(GroupEventDetails groupEventDetails)
	   {
		   GroupEventDto dto = new GroupEventDto();
		   if(groupEventDetails != null)
		   {
			    dto.setId(groupEventDetails.getId());
				dto.setGroupId(groupEventDetails.getGroupId());
				dto.setOrigin(groupEventDetails.getOrigin());
				dto.setDateOfEntry(groupEventDetails.getDateOfEntry());
				dto.setRoomId(groupEventDetails.getRoomId());
				dto.setEmployeeGroupId(groupEventDetails.getEmployeeGroupId());
				dto.setNumberOfPigs(groupEventDetails.getNumberOfPigs());
				dto.setWeightInKgs(groupEventDetails.getWeightInKgs());
				dto.setInventoryAdjustment(groupEventDetails.getInventoryAdjustment());
				dto.setGroupCloseDateTime(groupEventDetails.getGroupCloseDateTime());
				dto.setRemarks(groupEventDetails.getRemarks());
				dto.setLastUpdated(groupEventDetails.getLastUpdated());
				dto.setUserUpdated(groupEventDetails.getUserUpdated());
				dto.setPhaseOfProductionTypeId(dto.getPhaseOfProductionTypeId());
		   }
		   return dto;
	   }
	   
	   /**
	    * Convert list of GroupEventDetails to Dto
	    * @param pregnancyEvents
	    * @return
	    */
	   public List<GroupEventDto> convertToDtos(List<GroupEventDetails> groupEventDetailsL)
	   {
		   List<GroupEventDto> groupEventList = new ArrayList<GroupEventDto>();
		   for(GroupEventDetails groupEventDetails : groupEventDetailsL)
		   {
			   groupEventList.add(convertToDto(groupEventDetails));
		   }
		   return groupEventList;
	   }
	   
	   

}
