package com.pigtrax.pigevents.dto;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.util.DateUtil;

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
		   info.setUserUpdated(dto.getUserUpdated());
		   info.setCompanyId(dto.getCompanyId());
		   info.setRoomId(dto.getRoomId());
		   info.setBarnId(dto.getBarnId());
		   info.setSexTypeId(dto.getSexTypeId());
		   info.setActive(dto.isActive());
		   info.setGfunctionTypeId(dto.getGfunctionTypeId());
		   info.setOriginId(dto.getOriginId());
		   info.setPremiseId(dto.getPremiseId());
	   }
	   return info;
   }
   
   
   public PigInfoDto convertToDto(PigInfo info) throws ParseException
   {
	   PigInfoDto dto = new PigInfoDto();
	   if(info != null)
	   {
		   dto.setId(info.getId());
		   dto.setPigId(info.getPigId());
		   dto.setSireId(info.getSireId());
		   dto.setDamId(info.getDamId());
		   dto.setEntryDate(info.getEntryDate());
		   dto.setEntryDateStr(DateUtil.convertToFormatString(dto.getEntryDate(), "dd/MM/yyyy"));
		   dto.setOrigin(info.getOrigin());
		   dto.setGline(info.getGline());
		   dto.setGcompany(info.getGcompany());
		   dto.setBirthDate(info.getBirthDate());
		   dto.setBirthDateStr(DateUtil.convertToFormatString(dto.getBirthDate(), "dd/MM/yyyy"));
		   dto.setTattoo(info.getTattoo());
		   dto.setAlternateTattoo(info.getAlternateTattoo());
		   dto.setRemarks(info.getRemarks());
		   dto.setUserUpdated(info.getUserUpdated());
		   dto.setCompanyId(info.getCompanyId());
		   dto.setRoomId(info.getRoomId());
		   dto.setBarnId(info.getBarnId());
		   dto.setSexTypeId(info.getSexTypeId());
		   dto.setCurrentFarrowEventDate(info.getCurrentFarrowEventDate());
		   dto.setFarrowEventId(info.getFarrowId());
		   dto.setActive(info.isActive());
		   dto.setGfunctionTypeId(info.getGfunctionTypeId());
		   dto.setOriginId(info.getOriginId());
		   dto.setPremiseId(info.getPremiseId());
	   }
	   return dto;
   }
   
   public List<PigInfoDto> convertToDtos(List<PigInfo> piglist) throws ParseException
   {
	   List<PigInfoDto> pigDtolist = new ArrayList<PigInfoDto>();
	   for(PigInfo pig : piglist)
	   {
		   pigDtolist.add(convertToDto(pig));
	   }
	   return pigDtolist;
   }
}