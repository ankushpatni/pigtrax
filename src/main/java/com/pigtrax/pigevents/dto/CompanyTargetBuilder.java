package com.pigtrax.pigevents.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pigtrax.pigevents.beans.CompanyTarget;

@Component
public class CompanyTargetBuilder {

	 public CompanyTarget convertToBean(CompanyTargetDto dto)
	 {
		 if(dto != null)
		 {
			 CompanyTarget info = new CompanyTarget();
			 info.setId(dto.getId());
			 info.setCompanyId(dto.getCompanyId());
			 info.setTargetId(dto.getTargetId());
			 info.setTargetValue(dto.getTargetValue());
			 info.setRemarks(dto.getRemarks());
			 info.setCompletionDate(dto.getCompletionDate());
			 info.setLastUpdated(dto.getLastUpdated()); 
			 info.setUserUpdated(dto.getUserUpdated());
			 return info;
		 }
		 return null;
	 }
	 
	 
	 
	 public CompanyTargetDto convertToDto(CompanyTarget info)
	 {
		 if(info != null)
		 {
			 CompanyTargetDto dto = new CompanyTargetDto();
			 dto.setId(info.getId());
			 dto.setCompanyId(info.getCompanyId());
			 dto.setTargetId(info.getTargetId());
			 dto.setTargetValue(info.getTargetValue());
			 dto.setRemarks(info.getRemarks());
			 dto.setCompletionDate(info.getCompletionDate());
			 dto.setLastUpdated(info.getLastUpdated()); 
			 dto.setUserUpdated(info.getUserUpdated());
			 return dto;
		 } 
		 return null;
	 }
	 
	 
	 public List<CompanyTargetDto> convertToDtos(List<CompanyTarget> targets)
	 {
		 if(targets != null)
		 {
			 List<CompanyTargetDto> targetDtos= new ArrayList<CompanyTargetDto>();
			 for(CompanyTarget target : targets)
			 {
				 targetDtos.add(convertToDto(target));
			 }
			 return targetDtos;
			 
		 }
		 return null;
	 }
	
}


