package com.pigtrax.pigevents.dto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pigtrax.pigevents.beans.GroupEventDetails;
import com.pigtrax.pigevents.beans.PregnancyEvent;
import com.pigtrax.util.DateUtil;

@Component
public class GroupEventBuilder {
	
	/**
	 * Convert Bean to DTO
	 * @param dto
	 * @return
	 */
	public static GroupEventDetails convertToBean(GroupEventDto dto)
	   {
		GroupEventDetails groupEventDetails = new GroupEventDetails();
		   if(dto != null)
		   {
			   groupEventDetails.setId(dto.getId());
				groupEventDetails.setGroupId(dto.getGroupId());
				groupEventDetails.setBarnId(dto.getBarnId());
				groupEventDetails.setDateOfEntry(dto.getDateOfEntry());
				groupEventDetails.setRoomId(dto.getRoomId());
				groupEventDetails.setEmployeeGroupId(dto.getEmployeeGroupId());
				groupEventDetails.setNumberOfPigs(dto.getNumberOfPigs());
				groupEventDetails.setWeightInKgs(dto.getWeightInKgs());
				groupEventDetails.setInventoryAdjustment(dto.getInventoryAdjustment());
				groupEventDetails.setRemarks(dto.getRemarks());
				groupEventDetails.setLastUpdated(dto.getLastUpdated());
				groupEventDetails.setUserUpdated(dto.getUserUpdated());
				groupEventDetails.setTransportDestination(dto.getTransportDestination());
				groupEventDetails.setPremiseId(dto.getPremiseId());
				groupEventDetails.setSowSourceId(dto.getSowSourceId());
		   }
		   return groupEventDetails;
	   }
	
	 /**
	    * Convert DTO to Bean
	    * @param info
	    * @return
	    */
	   public static GroupEventDto convertToDto(GroupEventDetails groupEventDetails)
	   {
		   GroupEventDto dto = new GroupEventDto();
		   if(groupEventDetails != null)
		   {
			    dto.setId(groupEventDetails.getId());
				dto.setGroupId(groupEventDetails.getGroupId());
				dto.setBarnId(groupEventDetails.getBarnId());
				dto.setDateOfEntry(groupEventDetails.getDateOfEntry());
				try {
					dto.setDateOfEntryStr(DateUtil.convertToFormatString(dto.getDateOfEntry(), "MM/dd/yyyy"));
				} catch (ParseException e) {
					dto.setDateOfEntryStr(null);
				}
				dto.setRoomId(groupEventDetails.getRoomId());
				dto.setEmployeeGroupId(groupEventDetails.getEmployeeGroupId());
				dto.setNumberOfPigs(groupEventDetails.getNumberOfPigs());
				dto.setWeightInKgs(groupEventDetails.getWeightInKgs());
				dto.setInventoryAdjustment(groupEventDetails.getInventoryAdjustment());
				dto.setRemarks(groupEventDetails.getRemarks());
				dto.setLastUpdated(groupEventDetails.getLastUpdated());
				dto.setUserUpdated(groupEventDetails.getUserUpdated());
				dto.setTransportDestination(groupEventDetails.getTransportDestination());
				dto.setPremiseId(groupEventDetails.getPremiseId());
				dto.setSowSourceId(groupEventDetails.getSowSourceId());
		   }
		   return dto;
	   }
	   
	   /**
	    * Convert list of GroupEventDetails to Dto
	    * @param pregnancyEvents
	    * @return
	    */
	   public static List<GroupEventDto> convertToDtos(List<GroupEventDetails> groupEventDetailsL)
	   {
		   List<GroupEventDto> groupEventList = new ArrayList<GroupEventDto>();
		   for(GroupEventDetails groupEventDetails : groupEventDetailsL)
		   {
			   groupEventList.add(convertToDto(groupEventDetails));
		   }
		   return groupEventList;
	   }
	   
	   

}
