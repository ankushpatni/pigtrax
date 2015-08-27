package com.pigtrax.pigevents.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pigtrax.pigevents.beans.PigletStatusEvent;
import com.pigtrax.pigevents.beans.PregnancyEvent;
import com.pigtrax.usermanagement.enums.PigletStatusEventType;

@Component
public class PigletStatusEventBuilder {
	
	/**
	 * Convert Bean to DTO
	 * @param dto
	 * @return
	 */
	public PigletStatusEvent convertToBean(PigletStatusEventDto dto)
	   {
		PigletStatusEvent info = new PigletStatusEvent();
		   if(dto != null)
		   {
			   info.setId(dto.getId()); 
			   info.setEventDateTime(dto.getEventDateTime());
			   info.setEventReason(dto.getEventReason());
			   info.setFarrowEventId(dto.getFarrowEventId());
			   info.setFosterFrom(dto.getFosterFrom());
			   info.setFosterTo(dto.getFosterTo());
			   info.setLastUpdated(dto.getLastUpdated());
			   info.setNumberOfPigs(dto.getNumberOfPigs());
			   info.setWeightInKgs(dto.getWeightInKgs());
			   info.setPigInfoId(dto.getPigInfoId());
			   info.setPigletStatusEventTypeId(dto.getPigletStatusEventTypeId()); 
			   info.setRemarks(dto.getRemarks());
			   info.setSowCondition(dto.getSowCondition());
			   info.setUserUpdated(dto.getUserUpdated());
			   info.setWeanGroupId(dto.getWeanGroupId());
			   info.setFosterFarrowEventId(dto.getFosterFarrowId());
			   
		   }
		   return info;
	   }
	   
	   /**
	    * Convert DTO to Bean
	    * @param info
	    * @return
	    */
	   public PigletStatusEventDto convertToDto(PigletStatusEvent info)
	   {
		   PigletStatusEventDto dto = new PigletStatusEventDto();
		   
		   if(info != null)
		   { 
			   dto.setId(info.getId());
			   dto.setEventDateTime(info.getEventDateTime());
			   dto.setEventReason(info.getEventReason());
			   dto.setFarrowEventId(info.getFarrowEventId());
			   dto.setFosterFrom(info.getFosterFrom());
			   dto.setFosterTo(info.getFosterTo());
			   dto.setLastUpdated(info.getLastUpdated());
			   dto.setNumberOfPigs(info.getNumberOfPigs());
			   dto.setWeightInKgs(info.getWeightInKgs());
			   dto.setPigInfoId(info.getPigInfoId());
			   dto.setPigletStatusEventTypeId(info.getPigletStatusEventTypeId()); 
			   dto.setRemarks(info.getRemarks());
			   dto.setSowCondition(info.getSowCondition());
			   dto.setUserUpdated(info.getUserUpdated());
			   dto.setWeanGroupId(info.getWeanGroupId()); 
		   }		   
		   return dto; 
	   }
	   
	   
	   public PigletStatusEvent generateFosterInEvent(PigletStatusEvent pigletStatusEvent)
	   {
		   PigletStatusEvent fosterInEvent = null;
		   if(pigletStatusEvent != null)
		   {
			   fosterInEvent = new PigletStatusEvent();
			   fosterInEvent.setEventDateTime(pigletStatusEvent.getEventDateTime());
			   fosterInEvent.setPigInfoId(pigletStatusEvent.getFosterTo());
			   fosterInEvent.setFosterTo(pigletStatusEvent.getFosterTo());
			   fosterInEvent.setPigletStatusEventTypeId(PigletStatusEventType.FosterIn.getTypeCode());
			   fosterInEvent.setFosterFrom(pigletStatusEvent.getFosterFrom());
			   fosterInEvent.setNumberOfPigs(pigletStatusEvent.getNumberOfPigs());
			   fosterInEvent.setWeightInKgs(pigletStatusEvent.getWeightInKgs());
			   fosterInEvent.setEventReason(pigletStatusEvent.getEventReason());
			   fosterInEvent.setRemarks(pigletStatusEvent.getRemarks());
			   fosterInEvent.setSowCondition(null);
			   fosterInEvent.setWeanGroupId(pigletStatusEvent.getWeanGroupId());
			   fosterInEvent.setUserUpdated(pigletStatusEvent.getUserUpdated());
			   fosterInEvent.setFarrowEventId(pigletStatusEvent.getFosterFarrowEventId()); 
		   }
		   return fosterInEvent;
	   }
	   
	   /**
	    * Convert list of PreganancyEvent to Dto
	    * @param pregnancyEvents
	    * @return
	    */
	   public List<PigletStatusEventDto> convertToDtos(List<PigletStatusEvent> pigletStatusEvents)
	   {
		   List<PigletStatusEventDto> pigletStatusEventList = null;
		   if(pigletStatusEvents != null)
		   {
			   pigletStatusEventList = new ArrayList<PigletStatusEventDto>();
			   for(PigletStatusEvent event : pigletStatusEvents)
			   {
				   pigletStatusEventList.add(convertToDto(event));
			   }
		   }			   
		   return pigletStatusEventList;
	   }
	   
	   
	   
}
