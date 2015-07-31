package com.pigtrax.pigevents.dto;

import org.springframework.stereotype.Component;

import com.pigtrax.pigevents.beans.BreedingEvent;

@Component
public class BreedingEventBuilder {
	public BreedingEvent convertToBean(BreedingEventDto dto)
	   {
		BreedingEvent info = new BreedingEvent();
		   if(dto != null)
		   {
			   info.setId(dto.getId());
			   info.setServiceId(dto.getServiceId());
			   info.setEmployeeGroupId(dto.getEmployeeGroupId());
			   info.setPigInfoId(dto.getPigInfoId());
			   info.setPigInfoKey(dto.getPigInfoKey());
			   info.setBreedingServiceTypeId(dto.getBreedingServiceTypeId());
			   info.setBreedingGroupId(dto.getBreedingGroupId());
			   info.setBreedingDate(dto.getBreedingDate());
			   info.setSemenId(dto.getSemenId());
			   info.setRemarks(dto.getRemarks());
			   info.setSowCondition(dto.getSowCondition());
			   info.setUserUpdated(dto.getUserUpdated());
			   info.setMateQuality(dto.getMateQuality());
			   info.setLastUpdated(dto.getLastUpdated());
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
