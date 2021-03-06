package com.pigtrax.pigevents.dto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pigtrax.pigevents.beans.BreedingEvent;
import com.pigtrax.pigevents.beans.MatingDetails;
import com.pigtrax.util.DateUtil;

@Component
public class MatingDetailsBuilder {
	public MatingDetails convertToBean(MatingDetailsDto dto)
	   {
		MatingDetails info = new MatingDetails();
		   if(dto != null)
		   {
			   info.setMatingDetailId(dto.getMatingDetailId());
			   info.setEmployeeGroupId(dto.getEmployeeGroupId());
			   info.setBreedingEventId(dto.getBreedingEventId());
			   info.setLastUpdated(dto.getLastUpdated());
			   info.setMatingDate(dto.getMatingDate());
			   info.setMatingQuality(dto.getMatingQuality());
			   info.setSemenId(dto.getSemenId());
			   info.setUserUpdated(dto.getUserUpdated());
			   info.setSemenDate(dto.getSemenDate());
			   
		   }
		   return info;
	   }
	   
	   
	   public MatingDetailsDto convertToDto(MatingDetails info)
	   {
		   MatingDetailsDto dto = new MatingDetailsDto();
		   if(info != null)
		   {
			   dto.setMatingDetailId(info.getMatingDetailId());
			   dto.setEmployeeGroupId(info.getEmployeeGroupId());
			   dto.setBreedingEventId(info.getBreedingEventId());
			   dto.setLastUpdated(info.getLastUpdated());
			   dto.setMatingDate(info.getMatingDate());
			   try {
				dto.setMatingDateStr(DateUtil.convertToFormatString(info.getMatingDate(),"dd/MM/yyyy"));
			} catch (ParseException e) {
				dto.setMatingDateStr(null);
			}
			   
			   try {
					dto.setSemenDateStr(DateUtil.convertToFormatString(info.getSemenDate(),"dd/MM/yyyy"));
				} catch (ParseException e) {
					dto.setSemenDateStr(null);
				}
			   
			   dto.setMatingQuality(info.getMatingQuality());
			   dto.setSemenId(info.getSemenId());
			   dto.setUserUpdated(info.getUserUpdated());
			   dto.setSemenDate(info.getSemenDate());
		   }
		   return dto;
	   }
	   
	   /**
	    * Convert list of PreganancyEvent to Dto
	    * @param pregnancyEvents
	    * @return
	    */
	   public List<MatingDetailsDto> convertToDtos(List<MatingDetails> matingDetails)
	   {
		   List<MatingDetailsDto> matingDetailsDtoList = new ArrayList<MatingDetailsDto>();
		   for(MatingDetails event : matingDetails)
		   {
			   matingDetailsDtoList.add(convertToDto(event));
		   }
		   return matingDetailsDtoList;
	   }
}
