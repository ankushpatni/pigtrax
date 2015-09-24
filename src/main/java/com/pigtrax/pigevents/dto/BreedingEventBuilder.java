package com.pigtrax.pigevents.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pigtrax.pigevents.beans.BreedingEvent;
import com.pigtrax.pigevents.beans.PregnancyEvent;

@Component
public class BreedingEventBuilder {
	public BreedingEvent convertToBean(BreedingEventDto dto)
	   {
		BreedingEvent info = new BreedingEvent();
		   if(dto != null)
		   {
			   info.setId(dto.getId());
			   info.setPigInfoId(dto.getPigInfoKey());
			   info.setBreedingServiceTypeId(dto.getBreedingServiceTypeId());
			   info.setBreedingGroupId(dto.getBreedingGroupId());
			   info.setSowCondition(dto.getSowCondition());
			   info.setUserUpdated(dto.getUserUpdated());
			   info.setLastUpdated(dto.getLastUpdated());
			   info.setPenId(dto.getPenId());
			   info.setWeightInKgs(dto.getWeightInKgs());
		   }
		   return info;
	   }
	   
	   
	   public BreedingEventDto convertToDto(BreedingEvent info)
	   {
		   BreedingEventDto dto = new BreedingEventDto();
		   if(info != null)
		   {
			   dto.setId(info.getId());
			   dto.setPigInfoKey(info.getPigInfoId());
			   dto.setBreedingServiceTypeId(info.getBreedingServiceTypeId());
			   dto.setBreedingGroupId(info.getBreedingGroupId());
			   dto.setSowCondition(info.getSowCondition());
			   dto.setUserUpdated(info.getUserUpdated());
			   dto.setLastUpdated(info.getLastUpdated());
			   dto.setPenId(info.getPenId());
			   dto.setWeightInKgs(info.getWeightInKgs());
			   dto.setServiceStartDate(info.getServiceStartDate());
		   }
		   return dto;
	   }
	   
	   /**
	    * Convert list of PreganancyEvent to Dto
	    * @param pregnancyEvents
	    * @return
	    */
	   public List<BreedingEventDto> convertToDtos(List<BreedingEvent> breedingEvents)
	   {
		   List<BreedingEventDto> breedingEventList = new ArrayList<BreedingEventDto>();
		   if(breedingEvents != null && breedingEvents.size() > 0)
		   {
			   for(BreedingEvent event : breedingEvents)
			   {
				   breedingEventList.add(convertToDto(event));
			   }
		   }
		   return breedingEventList;
	   }
}
