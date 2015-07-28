package com.pigtrax.pigevents.dto;

import org.springframework.stereotype.Component;

import com.pigtrax.pigevents.beans.BreedingEvent;
import com.pigtrax.pigevents.beans.PregnancyEvent;

@Component
public class PregnancyEventBuilder {
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
	   
	   
	   public BreedingEventDto convertToDto(BreedingEvent info)
	   {
		   BreedingEventDto dto = new BreedingEventDto();
		   if(info != null)
		   {
			   dto.setId(info.getId());
			   dto.setServiceId(info.getServiceId());
			   dto.setEmployeeGroupId(info.getEmployeeGroupId());
			   dto.setPigInfoId(info.getPigInfoId());
			   dto.setPigInfoKey(info.getPigInfoKey());
			   dto.setBreedingServiceTypeId(info.getBreedingServiceTypeId());
			   dto.setBreedingGroupId(info.getBreedingGroupId());
			   dto.setBreedingDate(info.getBreedingDate());
			   dto.setSemenId(info.getSemenId());
			   dto.setRemarks(info.getRemarks());
			   dto.setSowCondition(info.getSowCondition());
			   dto.setUserUpdated(info.getUserUpdated());
			   dto.setMateQuality(info.getMateQuality());
			   dto.setLastUpdated(info.getLastUpdated());
		   }
		   return dto;
	   }
}
