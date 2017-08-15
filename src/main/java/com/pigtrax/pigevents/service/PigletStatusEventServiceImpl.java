package com.pigtrax.pigevents.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pigtrax.application.exception.PigTraxException;
import com.pigtrax.cache.RefDataCache;
import com.pigtrax.master.dao.interfaces.PenDao;
import com.pigtrax.master.dto.Pen;
import com.pigtrax.master.dto.Room;
import com.pigtrax.master.service.interfaces.BarnService;
import com.pigtrax.master.service.interfaces.RoomService;
import com.pigtrax.pigevents.beans.FarrowEvent;
import com.pigtrax.pigevents.beans.GroupEvent;
import com.pigtrax.pigevents.beans.GroupEventDetails;
import com.pigtrax.pigevents.beans.PigInfo;
import com.pigtrax.pigevents.beans.PigTraxEventMaster;
import com.pigtrax.pigevents.beans.PigletStatusEvent;
import com.pigtrax.pigevents.dao.interfaces.FarrowEventDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDao;
import com.pigtrax.pigevents.dao.interfaces.GroupEventDetailsDao;
import com.pigtrax.pigevents.dao.interfaces.PigInfoDao;
import com.pigtrax.pigevents.dao.interfaces.PigTraxEventMasterDao;
import com.pigtrax.pigevents.dao.interfaces.PigletStatusEventDao;
import com.pigtrax.pigevents.dto.PigletStatusEventBuilder;
import com.pigtrax.pigevents.dto.PigletStatusEventDto;
import com.pigtrax.pigevents.service.interfaces.FarrowEventService;
import com.pigtrax.pigevents.service.interfaces.PigletStatusEventService;
import com.pigtrax.pigevents.validation.PigletStatusEventValidation;
import com.pigtrax.usermanagement.enums.GroupEventActionType;
import com.pigtrax.usermanagement.enums.PigletStatusEventType;
import com.pigtrax.util.DateUtil;
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
	
	
	@Autowired
	GroupEventDao groupEventDao;
	
	@Autowired
	GroupEventDetailsDao groupEventDetailsDao;
	
	@Autowired
	RoomService roomService;
	
	@Autowired
	BarnService barnService;
	
	@Autowired
	PenDao penDao;
	
	 @Override
	public int savePigletStatusEvent(PigletStatusEventDto pigletStatusEventDto)
			throws PigTraxException {
		 PigletStatusEvent event = null;
		 
		int eventId = 0;
		try{
		if(pigletStatusEventDto != null)
		{
			
			PigInfo pigInfo = pigInfoDao.getPigInformationByPigId(pigletStatusEventDto.getPigId(), pigletStatusEventDto.getCompanyId(), pigletStatusEventDto.getPremiseId());
			pigletStatusEventDto.setPigInfoId(pigInfo.getId());
			
			
			boolean flag = setDerivedFarrowEventId(pigletStatusEventDto);
			
			if(flag)
			{
				event = builder.convertToBean(pigletStatusEventDto);
				//delete the records if present
				//deletePigletStatusEvent(pigletStatusEventDto);
		
				logger.info("farrow event ids : "+event.getFarrowEventId()+"/"+event.getFosterFarrowEventId());
				
				if(pigletStatusEventDto.getWeanPigNum() != null && pigletStatusEventDto.getWeanPigNum() > 0)
				{
					if(pigletStatusEventDto.getGroupId() != null && 0 <pigletStatusEventDto.getGroupId().length())
					{
						GroupEvent groupRecord = groupEventDao.getGroupEventByGroupId(pigletStatusEventDto.getGroupId(), pigletStatusEventDto.getCompanyId(), pigletStatusEventDto.getPremiseId());
						if(groupRecord != null)
							pigletStatusEventDto.setGroupEventId(groupRecord.getId());
					}
					
					
					//event.setFarrowEventId(pigletStatusEventDto.getFarrowEventId());
					event.setPigletStatusEventTypeId(PigletStatusEventType.Wean.getTypeCode());
					event.setNumberOfPigs(pigletStatusEventDto.getWeanPigNum());
					event.setWeightInKgs(pigletStatusEventDto.getWeanPigWt());
					event.setFosterFrom(null);
					event.setFosterTo(null);
					event.setFosterFarrowEventId(null);
					event.setEventDateTime(pigletStatusEventDto.getWeanEventDateTime());
					event.setGroupEventId(pigletStatusEventDto.getGroupEventId());
					event.setMortalityReasonTypeId(null);
					event.setPenId(pigletStatusEventDto.getPenId());
					event.setPremiseId(pigletStatusEventDto.getPremiseId());
					eventId = addPigletStatusEvent(event);
					
					if(pigletStatusEventDto.getGroupEventId() != null && pigletStatusEventDto.getGroupEventId() != 0)
					{
						
						//Update group event details
						updateGroupEventDetails(pigletStatusEventDto,eventId);
						
					}
					
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
					event.setEventDateTime(pigletStatusEventDto.getFosterEventDateTime());
					event.setGroupEventId(null);
					event.setMortalityReasonTypeId(null);
					event.setPenId(pigletStatusEventDto.getPenId());
					event.setPremiseId(pigletStatusEventDto.getPremiseId());
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
					event.setEventDateTime(pigletStatusEventDto.getDeathEventDateTime());
					event.setMortalityReasonTypeId(pigletStatusEventDto.getMortalityReasonTypeId());
					event.setGroupEventId(null);
					event.setPenId(pigletStatusEventDto.getPenId());
					event.setPremiseId(pigletStatusEventDto.getPremiseId());
					logger.info("farrow event id "+event.getFarrowEventId());
					eventId = addPigletStatusEvent(event);
				}	
			}
			else
				throw new PigTraxException("INVALID-FARROW");		
			
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
	 
	 
	 
	 private void updateGroupEventDetails(PigletStatusEventDto pigletStatusEventDto, Integer pigletStatusEventId) throws SQLException
	 {
		 	GroupEvent groupEvent = groupEventDao.getGroupEventByGeneratedGroupId(pigletStatusEventDto.getGroupEventId(), pigletStatusEventDto.getCompanyId());
			if(groupEvent != null)
			{
				Integer inventoryValue = groupEvent.getCurrentInventory();
				inventoryValue = inventoryValue+pigletStatusEventDto.getWeanPigNum();
				groupEvent.setCurrentInventory(inventoryValue);
				groupEventDao.updateGroupEventCurrentInventory(groupEvent);
				
				//add to group event details							
				GroupEventDetails groupEventDetails = new GroupEventDetails();
				if(pigletStatusEventDto.getPenId() != null)
				{
					Pen penObj = penDao.findPenByGeneratedId(pigletStatusEventDto.getPenId());
					if(penObj != null)
					{
						Room room = roomService.findByRoomByAutoGeneratedId(penObj.getRoomId());
						if(room != null)
						{
							groupEventDetails.setRoomId(room.getId());
							groupEventDetails.setBarnId(room.getBarnId());
						}
					}
				}
				groupEventDetails.setPigletStatusEventId(pigletStatusEventId);
				groupEventDetails.setPremiseId(pigletStatusEventDto.getPremiseId());
				groupEventDetails.setDateOfEntry(pigletStatusEventDto.getWeanEventDateTime());
				groupEventDetails.setNumberOfPigs(pigletStatusEventDto.getWeanPigNum());
				groupEventDetails.setWeightInKgs(pigletStatusEventDto.getWeanPigWt());
				groupEventDetails.setGroupId(pigletStatusEventDto.getGroupEventId());
				groupEventDetails.setUserUpdated(pigletStatusEventDto.getUserUpdated());
				groupEventDetails.setRemarks("From piglet wean");
				groupEventDetails.setGroupEventActionType(GroupEventActionType.Add.getTypeCode());
				groupEventDetails.setSowSourceId(pigletStatusEventDto.getPremiseId());
				groupEventDetailsDao.addGroupEventDetails(groupEventDetails);
				
				groupEventDao.updateGroupEventCurrentInventory(groupEvent);
			}
	 }
	 
	 private boolean setDerivedFarrowEventId(PigletStatusEventDto pigletStatusEventDto) throws SQLException
		{
			if(pigletStatusEventDto.getPigInfoId() != null)
			{
			 List<FarrowEvent> farrowEvents = farrowEventDao.getFarrowEvents(pigletStatusEventDto.getPigId(), "pigId", pigletStatusEventDto.getCompanyId());	
			 if(farrowEvents != null && 0 < farrowEvents.size())
			 {
				 for(FarrowEvent farrowEvent :  farrowEvents)
				 {
					 DateTime farrowDate = new DateTime(farrowEvent.getFarrowDateTime());
					 if(pigletStatusEventDto.getPigletStatusEventTypeId() == PigletStatusEventType.Wean.getTypeCode() && pigletStatusEventDto.getWeanPigNum() != null && pigletStatusEventDto.getWeanPigNum() > 0)
					 {
						 DateTime weanDate = new DateTime(pigletStatusEventDto.getWeanEventDateTime());
						 int duration = Days.daysBetween(farrowDate, weanDate).getDays();
						 if(duration >= 0 && duration <= 60)
						 {
							 pigletStatusEventDto.setFarrowEventId(farrowEvent.getId());
						 }
					 }
					 if(pigletStatusEventDto.getPigletStatusEventTypeId() == PigletStatusEventType.FosterOut.getTypeCode() && pigletStatusEventDto.getFosterPigNum() != null && pigletStatusEventDto.getFosterPigNum() > 0)
					 {
						 DateTime transferDate = new DateTime(pigletStatusEventDto.getFosterEventDateTime());
						 int duration = Days.daysBetween(farrowDate, transferDate).getDays();
						 if(duration >= 0 && duration <= 50 )
						 {
							 pigletStatusEventDto.setFarrowEventId(farrowEvent.getId());
						 }						
					 }
					 if(pigletStatusEventDto.getPigletStatusEventTypeId() == PigletStatusEventType.Death.getTypeCode() && pigletStatusEventDto.getDeathPigNum() != null && pigletStatusEventDto.getDeathPigNum() > 0)
					 {
						 DateTime deathDate = new DateTime(pigletStatusEventDto.getDeathEventDateTime());
						 int duration = Days.daysBetween(farrowDate, deathDate).getDays();
						 if(duration >= 0 && duration <= 50 )
						 {
							 pigletStatusEventDto.setFarrowEventId(farrowEvent.getId());
						 }
					 }
				 }
				 if(pigletStatusEventDto.getFarrowEventId() == null || pigletStatusEventDto.getFarrowEventId() == 0)
				 {
					 return false;
				 }
				 else
					 return true;
			 }
			 else
				 return false;
			}
			return false;
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
				
				if(pigletStatusEventDto.getId() != null && pigletStatusEventDto.getPigletStatusEventTypeId() == PigletStatusEventType.Wean.getTypeCode())
				{
					PigletStatusEvent statusEvent = pigletStatusEventDao.getPigletStatusEventInformation(pigletStatusEventDto.getId());
					if(statusEvent != null)
					{
						groupEventDetailsDao.deleteGroupEventDetailsByPigletEvent(statusEvent.getId());
						if(statusEvent.getGroupEventId() != null)
						{
							GroupEvent groupEvent = groupEventDao.getGroupEventByGeneratedGroupId(statusEvent.getGroupEventId(), pigletStatusEventDto.getCompanyId());
							if(groupEvent != null)
							{
								Integer newInvValue = groupEvent.getCurrentInventory() - statusEvent.getNumberOfPigs();
								groupEvent.setCurrentInventory(newInvValue);
								groupEventDao.updateGroupEventCurrentInventory(groupEvent);
							}
						
						}
					}
				}
				
				pigletStatusEventDao.deletePigletStatusEventsByFarrowId(pigletStatusEventDto.getPigInfoId(), pigletStatusEventDto.getFarrowEventId(), pigletStatusEventDto.getPigletStatusEventTypeId());
				if(pigletStatusEventDto.getPigletStatusEventTypeId() == 2)
					pigletStatusEventDao.deletePigletStatusEventsByFarrowId(pigletStatusEventDto.getPigInfoId(), pigletStatusEventDto.getFarrowEventId(), PigletStatusEventType.FosterIn.getTypeCode());
				
				
				if(pigletStatusEventDto.getId() != null)
					eventMasterDao.deletePigletStatusEvents( pigletStatusEventDto.getId(), pigletStatusEventDto.getPigletStatusEventTypeId());
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
						pigletEventStatusDto.getSearchOption(), pigletEventStatusDto.getCompanyId(), pigletEventStatusDto.getSelectedPremise());
				
				Integer farrowid =0;
				PigletStatusEventDto pigletStatusEventDto = new PigletStatusEventDto();
 				subList = new ArrayList<PigletStatusEventDto>();
			   for(PigletStatusEvent pigletStatusEvent : pigletStatusEvents)
			   { 
				   
				   //if(farrowid != pigletStatusEvent.getFarrowEventId()){
					   pigletStatusEventDto = new PigletStatusEventDto();
					   //generic fields
					   pigletStatusEventDto.setNumberOfPigsLW(pigletStatusEvent.getNumberOfPigsLW());
					   pigletStatusEventDto.setId(pigletStatusEvent.getId());
					   pigletStatusEventDto.setEventDateTime(pigletStatusEvent.getEventDateTime());
					   pigletStatusEventDto.setPigInfoId(pigletStatusEvent.getPigInfoId());
					   pigletStatusEventDto.setEventReason(pigletStatusEvent.getEventReason());
					   pigletStatusEventDto.setRemarks(pigletStatusEvent.getRemarks());
					   pigletStatusEventDto.setPenId(pigletStatusEvent.getPenId());
					   pigletStatusEventDto.setPremiseId(pigletStatusEvent.getPremiseId());
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
				   //} 

				   if(pigletStatusEvent.getPigletStatusEventTypeId().equals(PigletStatusEventType.Wean.getTypeCode())){
					   pigletStatusEventDto.setPigletStatusEventTypeId(PigletStatusEventType.Wean.getTypeCode());
					   pigletStatusEventDto.setWeanId(pigletStatusEvent.getId());
					   pigletStatusEventDto.setWeanPigNum(pigletStatusEvent.getNumberOfPigs());
					   pigletStatusEventDto.setWeanPigWt(pigletStatusEvent.getWeightInKgs());
					   pigletStatusEventDto.setWeanEventDateTime(pigletStatusEvent.getEventDateTime());
					   try{
						   pigletStatusEventDto.setWeanEventDateStr(DateUtil.convertToFormatString(pigletStatusEventDto.getWeanEventDateTime(), "dd/MM/yyyy"));
					   }catch(ParseException ex)
					   {
						   pigletStatusEventDto.setWeanEventDateStr(null);
					   }
					   pigletStatusEventDto.setGroupEventId(pigletStatusEvent.getGroupEventId());
					   
					   if(pigletStatusEvent.getGroupEventId() != null && pigletStatusEvent.getGroupEventId() > 0)
					   {
						   GroupEvent groupEvent = groupEventDao.getGroupEventByGeneratedGroupId(pigletStatusEvent.getGroupEventId(), pigletEventStatusDto.getCompanyId());
						   if(groupEvent != null)
							   pigletStatusEventDto.setGroupId(groupEvent.getGroupId());
					   }
					   
				   } else if(pigletStatusEvent.getPigletStatusEventTypeId().equals(PigletStatusEventType.FosterOut.getTypeCode())){
					   pigletStatusEventDto.setPigletStatusEventTypeId(PigletStatusEventType.FosterOut.getTypeCode());
					   pigletStatusEventDto.setFosterId(pigletStatusEvent.getId());
					   pigletStatusEventDto.setFosterPigNum(pigletStatusEvent.getNumberOfPigs());
					   pigletStatusEventDto.setFosterPigWt(pigletStatusEvent.getWeightInKgs());
					   pigletStatusEventDto.setFosterTo(pigletStatusEvent.getFosterTo());
					   pigletStatusEventDto.setFosterEventDateTime(pigletStatusEvent.getEventDateTime());
					   
					   try{
						   pigletStatusEventDto.setFosterEventDateStr(DateUtil.convertToFormatString(pigletStatusEventDto.getFosterEventDateTime(), "dd/MM/yyyy"));
					   }catch(ParseException ex)
					   {
						   pigletStatusEventDto.setFosterEventDateStr(null);
					   }
					   
					 	//Get the pig id for a given pigIdInfo
					   if(pigletStatusEvent.getFosterTo() != null && pigletStatusEvent.getFosterTo() > 0)
					   {
					   PigInfo fosterToPigInfo = pigInfoDao.getPigInformationById(pigletStatusEvent.getFosterTo());
					   pigletStatusEventDto.setFosterToPigId(fosterToPigInfo.getPigId());		
					   
					   
					   PigletStatusEvent fosterInRecord = pigletStatusEventDao.getFosterInRecord(pigletStatusEvent.getFarrowEventId());
					   pigletStatusEventDto.setFosterFarrowEventId(fosterInRecord.getFarrowEventId());
					   
					   }
					   
					   
					   
					   
				   } else if(pigletStatusEvent.getPigletStatusEventTypeId().equals(PigletStatusEventType.Death.getTypeCode())){
					   
					   pigletStatusEventDto.setPigletStatusEventTypeId(PigletStatusEventType.Death.getTypeCode());
					   pigletStatusEventDto.setDeathId(pigletStatusEvent.getId());
					   pigletStatusEventDto.setDeathPigNum(pigletStatusEvent.getNumberOfPigs());
					   pigletStatusEventDto.setDeathPigWt(pigletStatusEvent.getWeightInKgs());
					   pigletStatusEventDto.setDeathEventDateTime(pigletStatusEvent.getEventDateTime());
					   
					   try{
						   pigletStatusEventDto.setDeathEventDateStr(DateUtil.convertToFormatString(pigletStatusEventDto.getDeathEventDateTime(), "dd/MM/yyyy"));
					   }catch(ParseException ex)
					   {
						   pigletStatusEventDto.setDeathEventDateStr(null);
					   }
					   pigletStatusEventDto.setMortalityReasonTypeId(pigletStatusEvent.getMortalityReasonTypeId());
					   
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
	 
	 
	 @Override
		public List<PigletStatusEventDto> getPigletStatusEventsByFarrowEventId (Integer farrowEventId, Integer pigletStatusEventTypeId) throws PigTraxException {
			 List<PigletStatusEvent> pigletStatusEvents = pigletStatusEventDao.getPigletStatusEventsByFarrowEventId(farrowEventId, pigletStatusEventTypeId);		 
				List<PigletStatusEventDto> pigletStatusDtoEvents = builder.convertToDtos(pigletStatusEvents);		
				return pigletStatusDtoEvents;
		}
	 

}
