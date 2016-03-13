package com.pigtrax.pigevents.dto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pigtrax.pigevents.beans.PregnancyEvent;
import com.pigtrax.util.DateUtil;

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
			   info.setBreedingEventId(dto.getBreedingEventId());
			   info.setEmployeeGroupId(dto.getEmployeeGroupId());
			   info.setExamDate(dto.getExamDate());
			   info.setLastUpdated(dto.getLastUpdated());
			   info.setPigInfoId(dto.getPigInfoId());
			   info.setPregnancyEventTypeId(dto.getPregnancyEventTypeId());
			   info.setPregnancyExamResultTypeId(dto.getPregnancyExamResultTypeId());
			   info.setResultDate(dto.getResultDate());
			   info.setSowCondition(dto.getSowCondition());
			   info.setUserUpdated(dto.getUserUpdated());
			   info.setPremiseId(dto.getPremiseId());
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
			   dto.setBreedingEventId(info.getBreedingEventId());
			   dto.setEmployeeGroupId(info.getEmployeeGroupId());
			   dto.setExamDate(info.getExamDate());
			   dto.setLastUpdated(info.getLastUpdated());
			   dto.setPigInfoId(info.getPigInfoId());
			   dto.setPregnancyEventTypeId(info.getPregnancyEventTypeId());
			   dto.setPregnancyExamResultTypeId(info.getPregnancyExamResultTypeId());
			   dto.setResultDate(info.getResultDate());
			   try{
				   dto.setResultDateStr(DateUtil.convertToFormatString(dto.getResultDate(), "dd/MM/yyyy"));
			   }
			   catch(ParseException ex)
			   {
				   dto.setResultDateStr(null);
			   }
			   dto.setSowCondition(info.getSowCondition());
			   dto.setUserUpdated(info.getUserUpdated());
			   dto.setPremiseId(info.getPremiseId());
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
