package com.pigtrax.pigevents.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pigtrax.pigevents.beans.PigletEvent;
import com.pigtrax.pigevents.beans.PregnancyEvent;

@Component
public class PigletEventBuilder {
	
	/**
	 * Convert Bean to DTO
	 * @param dto
	 * @return
	 */
	public PigletEvent convertToBean(PigletEventDto dto)
	   {
		PigletEvent info = new PigletEvent();
		   if(dto != null)
		   {
			   info.setId(dto.getPigletId());
			   info.setTattooId(dto.getTattooId());
			   info.setWeightAtBirth(dto.getWeightAtBirth());
			   info.setWeightAtWeaning(dto.getWeightAtWeaning());
			   info.setLastUpdated(dto.getLastUpdated());
			   info.setUserUpdated(dto.getUserUpdated());
			   info.setFarrowEventId(dto.getFarrowEventId());
			   info.setPremiseId(dto.getPremiseId());
			   info.setLitterId(dto.getLitterId());
			   info.setPigInfoId(dto.getPigInfoId());
		   }
		   return info;
	   }
	   
	   /**
	    * Convert DTO to Bean
	    * @param info
	    * @return
	    */
	   public PigletEventDto convertToDto(PigletEvent info)
	   {
		   PigletEventDto dto = new PigletEventDto();
		   if(info != null)
		   {
			   dto.setPigletId(info.getId());
			   dto.setTattooId(info.getTattooId());
			   dto.setWeightAtBirth(info.getWeightAtBirth());
			   dto.setWeightAtWeaning(info.getWeightAtWeaning());
			   dto.setLastUpdated(info.getLastUpdated());
			   dto.setUserUpdated(info.getUserUpdated());
			   dto.setFarrowEventId(info.getFarrowEventId());
			   dto.setPremiseId(info.getPremiseId());
			   dto.setLitterId(info.getLitterId());
			   dto.setPigInfoId(info.getPigInfoId());
		   }
		   return dto;
	   }
	   
	   
	   /** 
	    * Convert list of PreganancyEvent to Dto
	    * @param pregnancyEvents
	    * @return
	    */
	   public List<PigletEventDto> convertToDtos(List<PigletEvent> pigletEvents)
	   {
		   List<PigletEventDto> pigletEventList = null;
		   if(pigletEvents != null)
		   {
			   pigletEventList = new ArrayList<PigletEventDto>();
			   for(PigletEvent event : pigletEvents)
			   {
				   pigletEventList.add(convertToDto(event));
			   }
		   }
		   return pigletEventList;
	   }
}
