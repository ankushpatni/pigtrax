package com.pigtrax.pigevents.dto;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.beans.PigletStatusEvent;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.usermanagement.enums.PigletStatusEventType;

@Component
public class PigletStatusEventBuilder {
	
	@Autowired
	PigInfoDao pigInfoDao;
	/**
	 * Convert Bean to DTO
	 * @param dto
	 * @return
	 */
	public PigletStatusEvent convertToBean(PigletStatusEventDto dto)
	   {
		PigletStatusEvent info = new PigletStatusEvent();
		   if(dto != null)
		   {
			   info.setId(dto.getId()); 
			   info.setEventDateTime(dto.getEventDateTime());
			   info.setEventReason(dto.getEventReason());
			   info.setFarrowEventId(dto.getFarrowEventId());
			   info.setFosterFrom(dto.getFosterFrom());
			   info.setFosterTo(dto.getFosterTo());
			   info.setLastUpdated(dto.getLastUpdated());
			   info.setNumberOfPigs(dto.getNumberOfPigs());
			   info.setWeightInKgs(dto.getWeightInKgs());
			   info.setPigInfoId(dto.getPigInfoId());
			   info.setPigletStatusEventTypeId(dto.getPigletStatusEventTypeId()); 
			   info.setRemarks(dto.getRemarks());
			   info.setSowCondition(dto.getSowCondition());
			   info.setUserUpdated(dto.getUserUpdated());
			   info.setWeanGroupId(dto.getWeanGroupId());
			   info.setFosterFarrowEventId(dto.getFosterFarrowEventId());
			   info.setGroupEventId(dto.getGroupEventId());
			   info.setMortalityReasonTypeId(dto.getMortalityReasonTypeId());
			    
		   }
		   return info;
	   }
	   
	   /**
	    * Convert DTO to Bean
	    * @param info
	    * @return
	    */
	   public PigletStatusEventDto convertToDto(PigletStatusEvent info) throws SQLException
	   {
		   PigletStatusEventDto dto = new PigletStatusEventDto();
		   PigInfo pigInfo = null;
		   
		   if(info != null)
		   { 
			   dto.setId(info.getId());
			   dto.setEventDateTime(info.getEventDateTime());
			   dto.setEventReason(info.getEventReason());
			   dto.setFarrowEventId(info.getFarrowEventId());
			   dto.setFosterFrom(info.getFosterFrom());
			   dto.setFosterTo(info.getFosterTo());
			   dto.setLastUpdated(info.getLastUpdated());
			   dto.setNumberOfPigs(info.getNumberOfPigs());
			   dto.setWeightInKgs(info.getWeightInKgs());
			   dto.setPigInfoId(info.getPigInfoId());
			   dto.setPigletStatusEventTypeId(info.getPigletStatusEventTypeId()); 
			   dto.setRemarks(info.getRemarks());
			   dto.setSowCondition(info.getSowCondition());
			   dto.setUserUpdated(info.getUserUpdated());
			   dto.setWeanGroupId(info.getWeanGroupId());
			   dto.setPigId(info.getPigId());
			   dto.setFosterFarrowEventId(info.getFosterFarrowEventId());
			   
			   if(dto.getFosterFrom() != null && dto.getFosterFrom() > 0)
			   {
				   pigInfo = pigInfoDao.getPigInformationById(dto.getFosterFrom());
				   dto.setFosterFromPigId(pigInfo.getPigId());		
			   }
			   if(dto.getFosterTo() != null && dto.getFosterTo() > 0)
			   {
				   pigInfo = pigInfoDao.getPigInformationById(dto.getFosterTo());
				   dto.setFosterToPigId(pigInfo.getPigId());		
			   }
			  
			   dto.setGroupEventId(info.getGroupEventId());
			   dto.setMortalityReasonTypeId(info.getMortalityReasonTypeId());
		   }		   
		   return dto; 
	   }
	   
	   
	   public PigletStatusEvent generateFosterInEvent(PigletStatusEventDto pigletStatusEventDto)
	   {
		   PigletStatusEvent fosterInEvent = null;
		   if(pigletStatusEventDto != null)
		   {
			   fosterInEvent = new PigletStatusEvent();
			   fosterInEvent.setEventDateTime(pigletStatusEventDto.getFosterEventDateTime());
			   fosterInEvent.setPigInfoId(pigletStatusEventDto.getFosterTo());
			   fosterInEvent.setFosterTo(pigletStatusEventDto.getFosterTo());
			   fosterInEvent.setPigletStatusEventTypeId(PigletStatusEventType.FosterIn.getTypeCode());
			   fosterInEvent.setFosterFrom(pigletStatusEventDto.getPigInfoId());
			   fosterInEvent.setNumberOfPigs(pigletStatusEventDto.getFosterPigNum());
			   fosterInEvent.setWeightInKgs(pigletStatusEventDto.getFosterPigWt());
			   fosterInEvent.setEventReason(pigletStatusEventDto.getEventReason());
			   fosterInEvent.setRemarks(pigletStatusEventDto.getRemarks());
			   fosterInEvent.setSowCondition(null);
			   fosterInEvent.setWeanGroupId(pigletStatusEventDto.getWeanGroupId());
			   fosterInEvent.setUserUpdated(pigletStatusEventDto.getUserUpdated());
			   fosterInEvent.setFarrowEventId(pigletStatusEventDto.getFosterFarrowEventId()); 
			   fosterInEvent.setFosterFarrowEventId(pigletStatusEventDto.getFarrowEventId());
		   }
		   System.out.println("before returning : "+fosterInEvent.getFarrowEventId()+"/"+fosterInEvent.getFosterFarrowEventId());
		   return fosterInEvent;
	   }
	   
	   /**
	    * Convert list of PreganancyEvent to Dto
	    * @param pregnancyEvents
	    * @return
	    */
	   public List<PigletStatusEventDto> convertToDtos(List<PigletStatusEvent> pigletStatusEvents)  throws PigTraxException
	   {
		   List<PigletStatusEventDto> pigletStatusEventList = null;
		   try{
		   if(pigletStatusEvents != null)
		   {
			   pigletStatusEventList = new ArrayList<PigletStatusEventDto>();
			   for(PigletStatusEvent event : pigletStatusEvents)
			   {
				   pigletStatusEventList.add(convertToDto(event));
			   }
		   }	
		   }catch(SQLException sqlEx)
		   {
			   throw new PigTraxException(sqlEx.getMessage(), sqlEx.getSQLState());
		   }
		   return pigletStatusEventList;
	   }
	   
	   
	   
}
