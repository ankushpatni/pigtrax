package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.cache.RefDataCache;
import com.pigtrax.pigevents.beans.FarrowEvent;
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
import com.pigtrax.usermanagement.enums.PigletStatusEventType;
@Service
public class PigletStatusEventServiceImpl implements PigletStatusEventService {

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
	
	 @Override
	public int savePigletStatusEvent(PigletStatusEventDto pigletStatusEventDto)
			throws PigTraxException {
		 PigletStatusEvent event = null;
		 
		int eventId = 0;
		try{
		if(pigletStatusEventDto != null)
		{
			FarrowEvent farrowEvent = farrowEventDao.getFarrowEvent(pigletStatusEventDto.getFarrowEventId());
			
			PigInfo pigInfo = pigInfoDao.getPigInformationByPigId(pigletStatusEventDto.getPigId(), pigletStatusEventDto.getCompanyId());
			pigletStatusEventDto.setPigInfoId(pigInfo.getId());
			
			event = builder.convertToBean(pigletStatusEventDto);
			//delete the records if present
			deletePigletStatusEvent(pigletStatusEventDto);
	
			if(pigletStatusEventDto.getWeanPigNum() != null && pigletStatusEventDto.getWeanPigNum() > 0 &&
					pigletStatusEventDto.getWeanPigWt() != null && pigletStatusEventDto.getWeanPigWt() > 0)
			{
				event.setPigletStatusEventTypeId(PigletStatusEventType.Wean.getTypeCode());
				event.setNumberOfPigs(pigletStatusEventDto.getWeanPigNum());
				event.setWeightInKgs(pigletStatusEventDto.getWeanPigWt());
				eventId = addPigletStatusEvent(event);
			}
			if(pigletStatusEventDto.getFosterPigNum() != null && pigletStatusEventDto.getFosterPigNum() > 0 &&
					pigletStatusEventDto.getFosterPigWt() != null && pigletStatusEventDto.getFosterPigWt() > 0)
			{
				event.setPigletStatusEventTypeId(PigletStatusEventType.FosterOut.getTypeCode());
				event.setNumberOfPigs(pigletStatusEventDto.getFosterPigNum());
				event.setWeightInKgs(pigletStatusEventDto.getFosterPigWt());
				event.setFosterFrom(pigletStatusEventDto.getPigInfoId());
				event.setFosterTo(pigletStatusEventDto.getFosterTo());
				eventId = addPigletStatusEvent(event);
				
				PigletStatusEvent fosterInEvent = builder.generateFosterInEvent(event);   
				eventId = addPigletStatusEvent(fosterInEvent);
			}
			
			if(pigletStatusEventDto.getDeathPigNum() != null && pigletStatusEventDto.getDeathPigNum() > 0 &&
					pigletStatusEventDto.getDeathPigWt() != null && pigletStatusEventDto.getDeathPigWt() > 0)
			{
				event.setPigletStatusEventTypeId(PigletStatusEventType.Death.getTypeCode());
				event.setNumberOfPigs(pigletStatusEventDto.getDeathPigNum());
				event.setWeightInKgs(pigletStatusEventDto.getDeathPigWt());
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
			
			eventMasterDao.insertEntryEventDetails(master);
			
			return pigletStatusId;		
		}  
	 
	 
	 @Transactional("ptxJTransactionManager")
		private int updatePigletStatusEvent(PigletStatusEvent pigletStatusEvent) throws SQLException
		{
			int pigletStatusId = pigletStatusEventDao.updatePigletStatusEvent(pigletStatusEvent);
			
			PigTraxEventMaster master = new PigTraxEventMaster();
			master.setPigInfoId(pigletStatusEvent.getPigInfoId());
			master.setUserUpdated(pigletStatusEvent.getUserUpdated());
			master.setEventTime(pigletStatusEvent.getEventDateTime());
			master.setPigletStatusId(pigletStatusEvent.getId());
			
			eventMasterDao.updatePigletStatusEventMasterDetails(master);
			
			return pigletStatusId;	 	
		}
	
	
	 
		/**
		 * Delete a pregnancy event based on the primary key ID
		 */
		@Override
		public void deletePigletStatusEvent(PigletStatusEventDto pigletStatusEventDto)
				throws PigTraxException {
			try{
				if(pigletStatusEventDto.getDeathId() != null)pigletStatusEventDao.deletePigletStatusEvent(pigletStatusEventDto.getDeathId());
				if(pigletStatusEventDto.getFosterId() != null)pigletStatusEventDao.deletePigletStatusEvent(pigletStatusEventDto.getFosterId());
				if(pigletStatusEventDto.getFosterinId() != null)pigletStatusEventDao.deletePigletStatusEvent(pigletStatusEventDto.getFosterinId());
				if(pigletStatusEventDto.getWeanId() != null)pigletStatusEventDao.deletePigletStatusEvent(pigletStatusEventDto.getWeanId());
			}
			catch(SQLException e)
			{
				throw new PigTraxException(e.getMessage(), e.getSQLState());
			}
		}	 
	 /**
	  * Get pigletStatus events based on pigId / Farrow ID
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
				//Map<Integer, List<PigletStatusEventDto>> pigletEventsMap = new  HashMap<Integer, List<PigletStatusEventDto>>();
				PigletStatusEventDto fosterInEvent = null;
				subList = new ArrayList<PigletStatusEventDto>();
			   for(PigletStatusEvent pigletStatusEvent : pigletStatusEvents)
			   {
				   
				   if(farrowid != pigletStatusEvent.getFarrowEventId()){
					   fosterInEvent = new PigletStatusEventDto();
					   //generic fields
					   fosterInEvent.setEventDateTime(pigletStatusEvent.getEventDateTime());
					   fosterInEvent.setPigInfoId(pigletStatusEvent.getPigInfoId());
					   fosterInEvent.setEventReason(pigletStatusEvent.getEventReason());
					   fosterInEvent.setRemarks(pigletStatusEvent.getRemarks());
					   fosterInEvent.setSowCondition(pigletStatusEvent.getSowCondition());
					   fosterInEvent.setWeanGroupId(pigletStatusEvent.getWeanGroupId());
					   fosterInEvent.setUserUpdated(pigletStatusEvent.getUserUpdated());
					   fosterInEvent.setFarrowEventId(pigletStatusEvent.getFarrowEventId()); 
					   //Get pregnancyEvent details 
					   fosterInEvent.setFarrowEventDto(farrowEventService.getFarrowEventDetails(pigletStatusEvent.getFarrowEventId()));  
						 
					   	//Get the pig id for a given pigIdInfo
					   PigInfo pigInfo = pigInfoDao.getPigInformationById(pigletStatusEvent.getPigInfoId());
					   fosterInEvent.setPigId(pigInfo.getPigId());					   
					   farrowid = pigletStatusEvent.getFarrowEventId();// set the next farrow id
					   subList.add(fosterInEvent);
				   } 

				   if(pigletStatusEvent.getPigletStatusEventTypeId().equals(PigletStatusEventType.Wean.getTypeCode())){
					   fosterInEvent.setWeanId(pigletStatusEvent.getId());
					   fosterInEvent.setWeanPigNum(pigletStatusEvent.getNumberOfPigs());
					   fosterInEvent.setWeanPigWt(pigletStatusEvent.getWeightInKgs());
					   
				   } else if(pigletStatusEvent.getPigletStatusEventTypeId().equals(PigletStatusEventType.FosterOut.getTypeCode())){
					   fosterInEvent.setFosterId(pigletStatusEvent.getId());
					   fosterInEvent.setFosterPigNum(pigletStatusEvent.getNumberOfPigs());
					   fosterInEvent.setFosterPigWt(pigletStatusEvent.getWeightInKgs());
					   fosterInEvent.setFosterTo(pigletStatusEvent.getFosterTo());
					   
				   } else if(pigletStatusEvent.getPigletStatusEventTypeId().equals(PigletStatusEventType.Death.getTypeCode())){
					   fosterInEvent.setDeathId(pigletStatusEvent.getId());
					   fosterInEvent.setDeathPigNum(pigletStatusEvent.getNumberOfPigs());
					   fosterInEvent.setDeathPigWt(pigletStatusEvent.getWeightInKgs());
					   
				   } else{ //foster in case					   
					   fosterInEvent.setFosterinId(pigletStatusEvent.getId()); 
				   }
				   
				   
				   
			   }
			   
				return subList; 
				
			} catch (SQLException e) {
				throw new PigTraxException(e.getMessage(), e.getSQLState());
			}
	}
	 
	  private Map<Integer, List<PigletStatusEventDto>> populateMap(List<PigletStatusEventDto> pigletStatusEventDtoList)
	  {
		  Map<Integer, List<PigletStatusEventDto>> pigletEventsMap = new  HashMap<Integer, List<PigletStatusEventDto>>();
		  List<PigletStatusEventDto> subList = null;
		  if(pigletStatusEventDtoList != null)
		  {
			  
			  int farrowEventId = 0;
			  for(PigletStatusEventDto dto : pigletStatusEventDtoList)
			  {	 
				  if(dto.getFarrowEventId() != farrowEventId)
				  {
					  subList = new ArrayList<PigletStatusEventDto>();
					  pigletEventsMap.put(dto.getFarrowEventId(), subList);
					  farrowEventId = dto.getFarrowEventId();
				  }
				  subList.add(dto);
			  }
		  }
		  return pigletEventsMap;
	  }
}
