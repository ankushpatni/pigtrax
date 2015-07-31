package com.pigtrax.pigevents.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pigtrax.pigevents.beans.PregnancyEvent;

@Component
public class PregnancyEventBuilder {
	
	/**
	 * Convert Bean to DTO
	 * @param dto
	 * @return
	 */
	public PregnancyEvent convertToBean(PregnancyEventDto dto)
	   {
		PregnancyEvent info = new PregnancyEvent();
		   if(dto != null)
		   {
			   info.setId(dto.getId());
			   info.setEmployeeGroupId(dto.getEmployeeGroupId());
			   info.setExamDate(dto.getExamDate());
			   info.setLastUpdated(dto.getLastUpdated());
			   info.setPigInfoId(dto.getPigInfoId());
			   info.setPregnancyEventTypeId(dto.getPregnancyEventTypeId());
			   info.setPregnancyExamResultTypeId(dto.getPregnancyExamResultTypeId());
			   info.setResultDate(dto.getResultDate());
			   info.setSowCondition(dto.getSowCondition());
			   info.setUserUpdated(dto.getUserUpdated());
		   }
		   return info;
	   }
	   
	   /**
	    * Convert DTO to Bean
	    * @param info
	    * @return
	    */
	   public PregnancyEventDto convertToDto(PregnancyEvent info)
	   {
		   PregnancyEventDto dto = new PregnancyEventDto();
		   if(info != null)
		   {
			   dto.setId(info.getId());
			   dto.setEmployeeGroupId(info.getEmployeeGroupId());
			   dto.setExamDate(info.getExamDate());
			   dto.setLastUpdated(info.getLastUpdated());
			   dto.setPigInfoId(info.getPigInfoId());
			   dto.setPregnancyEventTypeId(info.getPregnancyEventTypeId());
			   dto.setPregnancyExamResultTypeId(info.getPregnancyExamResultTypeId());
			   dto.setResultDate(info.getResultDate());
			   dto.setSowCondition(info.getSowCondition());
			   dto.setUserUpdated(info.getUserUpdated());
		   }
		   return dto;
	   }
	   
	   
	   /**
	    * Convert list of PreganancyEvent to Dto
	    * @param pregnancyEvents
	    * @return
	    */
	   public List<PregnancyEventDto> convertToDtos(List<PregnancyEvent> pregnancyEvents)
	   {
		   List<PregnancyEventDto> pregnancyEventList = new ArrayList<PregnancyEventDto>();
		   for(PregnancyEvent event : pregnancyEvents)
		   {
			   pregnancyEventList.add(convertToDto(event));
		   }
		   return pregnancyEventList;
	   }
}
