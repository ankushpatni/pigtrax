package com.pigtrax.pigevents.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pigtrax.pigevents.beans.FarrowEvent;
import com.pigtrax.pigevents.beans.PregnancyEvent;

@Component
public class FarrowEventBuilder {
	
	/**
	 * Convert Bean to DTO
	 * @param dto
	 * @return
	 */
	public FarrowEvent convertToBean(FarrowEventDto dto)
	   {
		FarrowEvent info = new FarrowEvent();
		   if(dto != null)
		   {
			   info.setId(dto.getId());
			   info.setAssistedBirth(dto.isAssistedBirth());
			   info.setEmployeeGroupId(dto.getEmployeeGroupId());
			   info.setFarrowDateTime(dto.getFarrowDateTime());
			   info.setFemaleBorns(dto.getFemaleBorns());
			   info.setId(dto.getId());
			   info.setInducedBirth(dto.isInducedBirth());
			   info.setLastUpdated(dto.getLastUpdated());
			   info.setLiveBorns(dto.getLiveBorns());
			   info.setMaleBorns(dto.getMaleBorns());
			   info.setMummies(dto.getMummies());
			   info.setPenId(dto.getPenId());
			   info.setPigInfoId(dto.getPigInfoId());
			   info.setPregnancyEventId(dto.getPregnancyEventId());
			   info.setRemarks(dto.getRemarks());
			   info.setSowCondition(dto.getSowCondition());
			   info.setStillBorns(dto.getStillBorns());
			   info.setUserUpdated(dto.getUserUpdated());
			   info.setWeightInKgs(dto.getWeightInKgs());
			   info.setPregnancyEventId(dto.getPregnancyEventId());
			   info.setTeats(dto.getTeats());
			   info.setPigletConditionId(dto.getPigletConditionId());
			   info.setWeakBorns(dto.getWeakBorns());
		   }
		   return info;
	   }
	   
	   /**
	    * Convert DTO to Bean
	    * @param info
	    * @return
	    */
	   public FarrowEventDto convertToDto(FarrowEvent info)
	   {
		   FarrowEventDto dto = new FarrowEventDto();
		   if(info != null)
		   {
			   dto.setId(info.getId());
			   dto.setAssistedBirth(info.isAssistedBirth());
			   dto.setEmployeeGroupId(info.getEmployeeGroupId());
			   dto.setFarrowDateTime(info.getFarrowDateTime());
			   dto.setFemaleBorns(info.getFemaleBorns());
			   dto.setId(info.getId());
			   dto.setInducedBirth(info.isInducedBirth());
			   dto.setLastUpdated(info.getLastUpdated());
			   dto.setLiveBorns(info.getLiveBorns());
			   dto.setMaleBorns(info.getMaleBorns());
			   dto.setMummies(info.getMummies());
			   dto.setPenId(info.getPenId());
			   dto.setPigInfoId(info.getPigInfoId());
			   dto.setPregnancyEventId(info.getPregnancyEventId());
			   dto.setRemarks(info.getRemarks());
			   dto.setSowCondition(info.getSowCondition());
			   dto.setStillBorns(info.getStillBorns());
			   dto.setUserUpdated(info.getUserUpdated());
			   dto.setWeightInKgs(info.getWeightInKgs());
			   dto.setPregnancyEventId(info.getPregnancyEventId());
			   dto.setTeats(info.getTeats());
			   dto.setPigletConditionId(info.getPigletConditionId());
			   dto.setWeakBorns(info.getWeakBorns());
		   }
		   return dto;
	   }
	   
	   
	   /**
	    * Convert list of PreganancyEvent to Dto
	    * @param farrowEvents
	    * @return
	    */
	   public List<FarrowEventDto> convertToDtos(List<FarrowEvent> farrowEvents)
	   {
		   List<FarrowEventDto> farrowEventList = new ArrayList<FarrowEventDto>();
		   for(FarrowEvent event : farrowEvents)
		   {
			   farrowEventList.add(convertToDto(event));
		   }
		   return farrowEventList;
	   }
}
