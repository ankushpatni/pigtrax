package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.cache.RefDataCache;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.beans.PigletStatusEvent;
import com.pigtrax.pigevents.dao.interfaces.FarrowEventDao;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.dao.interfaces.PigletStatusEventDao;
import com.pigtrax.pigevents.dto.PigletStatusEventBuilder;
import com.pigtrax.pigevents.dto.PigletStatusEventDto;
import com.pigtrax.pigevents.service.interfaces.FarrowEventService;
import com.pigtrax.pigevents.service.interfaces.PigletStatusEventService;
import com.pigtrax.pigevents.validation.PigletStatusEventValidation;
import com.pigtrax.usermanagement.enums.PigletStatusEventType;
@Service
public class PigletStatusEventServiceImpl implements PigletStatusEventService {
	
	private static final Logger logger = Logger.getLogger(PigletStatusEventServiceImpl.class);
	
	@Autowired
	FarrowEventDao farrowEventDao;
	
	@Autowired
	PigletStatusEventBuilder builder;
	
	@Autowired
	PigletStatusEventDao pigletStatusEventDao;
	
	@Autowired
	PigTraxEventMasterDao eventMasterDao;
	
	@Autowired
	RefDataCache cache;
	
	@Autowired
	PigInfoDao pigInfoDao;
	
	@Autowired
	FarrowEventService farrowEventService;
	
	@Autowired
	PigletStatusEventValidation validationObj;	
	
	 @Override
	public int savePigletStatusEvent(PigletStatusEventDto pigletStatusEventDto)
			throws PigTraxException {
		 PigletStatusEvent event = null;
		 
		int eventId = 0;
		try{
		if(pigletStatusEventDto != null)
		{
			
			PigInfo pigInfo = pigInfoDao.getPigInformationByPigId(pigletStatusEventDto.getPigId(), pigletStatusEventDto.getCompanyId());
			pigletStatusEventDto.setPigInfoId(pigInfo.getId());
			
			event = builder.convertToBean(pigletStatusEventDto);
			//delete the records if present
			deletePigletStatusEvent(pigletStatusEventDto);
	
			logger.info("farrow event ids : "+event.getFarrowEventId()+"/"+event.getFosterFarrowEventId());
			
			if(pigletStatusEventDto.getWeanPigNum() != null && pigletStatusEventDto.getWeanPigNum() > 0)
			{
				event.setFarrowEventId(pigletStatusEventDto.getFarrowEventId());
				event.setPigletStatusEventTypeId(PigletStatusEventType.Wean.getTypeCode());
				event.setNumberOfPigs(pigletStatusEventDto.getWeanPigNum());
				event.setWeightInKgs(pigletStatusEventDto.getWeanPigWt());
				event.setFosterFrom(null);
				event.setFosterTo(null);
				event.setFosterFarrowEventId(null);
				eventId = addPigletStatusEvent(event);
			}
			if(pigletStatusEventDto.getFosterPigNum() != null && pigletStatusEventDto.getFosterPigNum() > 0)
			{
				event.setFarrowEventId(pigletStatusEventDto.getFarrowEventId());
				event.setPigletStatusEventTypeId(PigletStatusEventType.FosterOut.getTypeCode());
				event.setNumberOfPigs(pigletStatusEventDto.getFosterPigNum());
				event.setWeightInKgs(pigletStatusEventDto.getFosterPigWt());
				event.setFosterFrom(pigletStatusEventDto.getPigInfoId());
				event.setFosterTo(pigletStatusEventDto.getFosterTo());
				event.setFosterFarrowEventId(null);
				eventId = addPigletStatusEvent(event);
				
				PigletStatusEvent fosterInEvent = builder.generateFosterInEvent(pigletStatusEventDto);   
				eventId = addPigletStatusEvent(fosterInEvent);
			}
			
			if(pigletStatusEventDto.getDeathPigNum() != null && pigletStatusEventDto.getDeathPigNum() > 0)
			{
				event.setFarrowEventId(pigletStatusEventDto.getFarrowEventId()); 
				event.setPigletStatusEventTypeId(PigletStatusEventType.Death.getTypeCode());
				event.setNumberOfPigs(pigletStatusEventDto.getDeathPigNum());
				event.setWeightInKgs(pigletStatusEventDto.getDeathPigWt());
				event.setFosterFrom(null);
				event.setFosterTo(null);
				event.setFosterFarrowEventId(null);
				logger.info("farrow event id "+event.getFarrowEventId());
				eventId = addPigletStatusEvent(event);
			}					
			
			}
		}catch(SQLException e)
		{
			throw new PigTraxException(e.getMessage(), e.getSQLState());
		}
		catch(DuplicateKeyException e)
		{
			throw new PigTraxException(e.getMessage(), "", true);
		}
		return eventId;
	}
	 
	 
	 @Transactional("ptxJTransactionManager")
		private int addPigletStatusEvent(PigletStatusEvent pigletStatusEvent) throws SQLException
		{
			int pigletStatusId = pigletStatusEventDao.addPigletStatusEvent(pigletStatusEvent); 
			
			PigTraxEventMaster master = new PigTraxEventMaster();
			master.setPigInfoId(pigletStatusEvent.getPigInfoId());
			master.setUserUpdated(pigletStatusEvent.getUserUpdated());
			master.setEventTime(pigletStatusEvent.getEventDateTime());
			master.setPigletStatusId(pigletStatusId);
			master.setFarrowEventId(pigletStatusEvent.getFarrowEventId());
			
			eventMasterDao.insertEntryEventDetails(master);
			
			return pigletStatusId;		
		}
	
	 //validate Piglet status event
		@Override
		public int validatePigletStatusEvent(PigletStatusEventDto pigletStatusEventDto)
		{
			try {				
				return validationObj.validate(pigletStatusEventDto);
			}
			catch (PigTraxException e) {
				e.printStackTrace();
				return -1;
			}
		}	 
	
	 
		/**
		 * Delete all Piglet status event based on the primary key ID
		 */
		@Override
		public void deletePigletStatusEvent(PigletStatusEventDto pigletStatusEventDto)
				throws PigTraxException {
			try{  
				pigletStatusEventDao.deletePigletStatusEventsByFarrowId(pigletStatusEventDto.getFarrowEventId());				
			}
			catch(SQLException e)
			{
			  throw new PigTraxException(e.getMessage(), e.getSQLState()); 
			}
		}	 
	 /**
	  * Get pigletStatus events based on Pig Id / Tattoo ID
	  */
	 @Override
	public List<PigletStatusEventDto> getPigletStatusEvents(
			PigletStatusEventDto pigletEventStatusDto) throws PigTraxException { 
		 List<PigletStatusEvent> pigletStatusEvents = null;
		 List<PigletStatusEventDto> subList = null;
			try {
				pigletStatusEvents = pigletStatusEventDao.getPigletStatusEvents(pigletEventStatusDto.getSearchText(), 
						pigletEventStatusDto.getSearchOption(), pigletEventStatusDto.getCompanyId());
				
				Integer farrowid =0;
				PigletStatusEventDto pigletStatusEventDto = new PigletStatusEventDto();
 				subList = new ArrayList<PigletStatusEventDto>();
			   for(PigletStatusEvent pigletStatusEvent : pigletStatusEvents)
			   { 
				   
				   if(farrowid != pigletStatusEvent.getFarrowEventId()){
					   pigletStatusEventDto = new PigletStatusEventDto();
					   //generic fields
					   pigletStatusEventDto.setEventDateTime(pigletStatusEvent.getEventDateTime());
					   pigletStatusEventDto.setPigInfoId(pigletStatusEvent.getPigInfoId());
					   pigletStatusEventDto.setEventReason(pigletStatusEvent.getEventReason());
					   pigletStatusEventDto.setRemarks(pigletStatusEvent.getRemarks());
					   pigletStatusEventDto.setSowCondition(pigletStatusEvent.getSowCondition());
					   pigletStatusEventDto.setWeanGroupId(pigletStatusEvent.getWeanGroupId());
					   pigletStatusEventDto.setUserUpdated(pigletStatusEvent.getUserUpdated());
					   pigletStatusEventDto.setFarrowEventId(pigletStatusEvent.getFarrowEventId()); 
					   //Get pregnancyEvent details 
					   pigletStatusEventDto.setFarrowEventDto(farrowEventService.getFarrowEventDetails(pigletStatusEvent.getFarrowEventId()));  
						 
					   	//Get the pig id for a given pigIdInfo
					   PigInfo pigInfo = pigInfoDao.getPigInformationById(pigletStatusEvent.getPigInfoId());
					   pigletStatusEventDto.setPigId(pigInfo.getPigId());					   
					   farrowid = pigletStatusEvent.getFarrowEventId();// set the next farrow id
					   subList.add(pigletStatusEventDto);
				   } 

				   if(pigletStatusEvent.getPigletStatusEventTypeId().equals(PigletStatusEventType.Wean.getTypeCode())){
					   pigletStatusEventDto.setWeanId(pigletStatusEvent.getId());
					   pigletStatusEventDto.setWeanPigNum(pigletStatusEvent.getNumberOfPigs());
					   pigletStatusEventDto.setWeanPigWt(pigletStatusEvent.getWeightInKgs());
					   
				   } else if(pigletStatusEvent.getPigletStatusEventTypeId().equals(PigletStatusEventType.FosterOut.getTypeCode())){
					   pigletStatusEventDto.setFosterId(pigletStatusEvent.getId());
					   pigletStatusEventDto.setFosterPigNum(pigletStatusEvent.getNumberOfPigs());
					   pigletStatusEventDto.setFosterPigWt(pigletStatusEvent.getWeightInKgs());
					   pigletStatusEventDto.setFosterTo(pigletStatusEvent.getFosterTo());
					 	//Get the pig id for a given pigIdInfo
					   if(pigletStatusEvent.getFosterTo() != null && pigletStatusEvent.getFosterTo() > 0)
					   {
					   PigInfo fosterToPigInfo = pigInfoDao.getPigInformationById(pigletStatusEvent.getFosterTo());
					   pigletStatusEventDto.setFosterToPigId(fosterToPigInfo.getPigId());		
					   
					   
					   PigletStatusEvent fosterInRecord = pigletStatusEventDao.getFosterInRecord(pigletStatusEvent.getFarrowEventId());
					   pigletStatusEventDto.setFosterFarrowEventId(fosterInRecord.getFarrowEventId());
					   
					   }
					   
					   
					   
					   
				   } else if(pigletStatusEvent.getPigletStatusEventTypeId().equals(PigletStatusEventType.Death.getTypeCode())){
					   pigletStatusEventDto.setDeathId(pigletStatusEvent.getId());
					   pigletStatusEventDto.setDeathPigNum(pigletStatusEvent.getNumberOfPigs());
					   pigletStatusEventDto.setDeathPigWt(pigletStatusEvent.getWeightInKgs());
				   } else{ //foster in case					   
					   pigletStatusEventDto.setFosterinId(pigletStatusEvent.getId()); 
					   
				   }				      
				   
			   }
			   
				return subList; 
				
			} catch (SQLException e) {
				throw new PigTraxException(e.getMessage(), e.getSQLState());
			}
	}
	 
	 
	 /**
	  * To retrieve the foster In records for a given Pig Info Id
	  */
	 @Override
	public List<PigletStatusEventDto> getFosterInRecords(String pigId, Integer companyId, Integer farrowEventId) throws PigTraxException { 
		List<PigletStatusEvent> fosterInRecords = pigletStatusEventDao.getFosterInRecords(pigId, companyId, farrowEventId);		
		List<PigletStatusEventDto> fosterInDtoRecords = builder.convertToDtos(fosterInRecords);		
		return fosterInDtoRecords;
	}
	  
	 @Override
	public List<PigletStatusEventDto> getPigletStatusEventsByFarrowEventId (Integer farrowEventId) throws PigTraxException {
		 List<PigletStatusEvent> pigletStatusEvents = pigletStatusEventDao.getPigletStatusEventsByFarrowEventId(farrowEventId);		 
			List<PigletStatusEventDto> pigletStatusDtoEvents = builder.convertToDtos(pigletStatusEvents);		
			return pigletStatusDtoEvents;
	}
	 

}
