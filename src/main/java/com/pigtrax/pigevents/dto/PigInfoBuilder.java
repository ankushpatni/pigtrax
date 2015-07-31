package com.pigtrax.pigevents.dto;

import org.springframework.stereotype.Component;

import com.pigtrax.pigevents.beans.PigInfo;

@Component
public class PigInfoBuilder {
   public PigInfo convertToBean(PigInfoDto dto)
   {
	   PigInfo info = new PigInfo();
	   if(dto != null)
	   {
		   info.setId(dto.getId());
		   info.setPigId(dto.getPigId());
		   info.setSireId(dto.getSireId());
		   info.setDamId(dto.getDamId());
		   info.setEntryDate(dto.getEntryDate());
		   info.setOrigin(dto.getOrigin());
		   info.setGline(dto.getGline());
		   info.setGcompany(dto.getGcompany());
		   info.setBirthDate(dto.getBirthDate());
		   info.setTattoo(dto.getTattoo());
		   info.setAlternateTattoo(dto.getAlternateTattoo());
		   info.setRemarks(dto.getRemarks());
		   info.setSowCondition(dto.getSowCondition());
		   info.setUserUpdated(dto.getUserUpdated());
		   info.setCompanyId(dto.getCompanyId());
		   info.setPenId(dto.getPenId());
		   info.setBarnId(dto.getBarnId());
		   info.setSexTypeId(dto.getSexTypeId());
	   }
	   return info;
   }
   
   
   public PigInfoDto convertToDto(PigInfo info)
   {
	   PigInfoDto dto = new PigInfoDto();
	   if(info != null)
	   {
		   dto.setId(info.getId());
		   dto.setPigId(info.getPigId());
		   dto.setSireId(info.getSireId());
		   dto.setDamId(info.getDamId());
		   dto.setEntryDate(info.getEntryDate());
		   dto.setOrigin(info.getOrigin());
		   dto.setGline(info.getGline());
		   dto.setGcompany(info.getGcompany());
		   dto.setBirthDate(info.getBirthDate());
		   dto.setTattoo(info.getTattoo());
		   dto.setAlternateTattoo(info.getAlternateTattoo());
		   dto.setRemarks(info.getRemarks());
		   dto.setSowCondition(info.getSowCondition());
		   dto.setUserUpdated(info.getUserUpdated());
		   dto.setCompanyId(info.getCompanyId());
		   dto.setPenId(info.getPenId());
		   dto.setBarnId(info.getBarnId());
		   dto.setSexTypeId(info.getSexTypeId());
	   }
	   return dto;
   }
}
